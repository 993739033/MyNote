package daniel.avila.rnm.kmm.presentation.mvi

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// view-> event(intent) -> model -> state->view
abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ScreenModel {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    protected val currentState: State
        get() = uiState.value

    //单个 只发送最新数据 防抖 不变化不处理
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    //只读 只返回最新
    val uiState = _uiState.asStateFlow()

    //重复数据也会发送 挂起
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    //只读
    val event = _event.asSharedFlow()

    // 只会消费一次 挂起
    private val _effect: Channel<Effect> = Channel()

    ////只能被collect一次，多次收集抛异常。
    //consumeAsFlow() 能被collect多次，但一个元素只能被消费一次。
    val effect = _effect.receiveAsFlow()

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        subscribeEvents()
    }

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        coroutineScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)

    /**
     * Set new Event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        coroutineScope.launch { _event.emit(newEvent) }
    }

    /**
     * Set new Ui State
     */
    //局部扩展方法
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        coroutineScope.launch { _effect.send(effectValue) }
    }
}