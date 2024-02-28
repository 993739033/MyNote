package org.scode.mynote.base.mvi

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.company.app.utils.Log
import org.scode.mynote.ui.event.GobalEvent
import org.scode.mynote.ui.screen.detail.DetailScreen
import org.scode.mynote.view.LoadingDialog
import org.scode.mynote.view.ToastDialog

abstract class BaseScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    companion object {
        // 只会消费一次 挂起
        val _gobalEvent: Channel<GobalEvent> = Channel()
        val gobalEvent = _gobalEvent.receiveAsFlow()
    }

    //只返回最新
    var coroutineScope = CoroutineScope(Dispatchers.IO)

    fun sendGobalEvent(event: GobalEvent) {
        coroutineScope.launch {
            _gobalEvent.send(event)
        }
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var show = remember { mutableStateOf(false) }
        var toataData by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            gobalEvent.collect() {
                Log.e("gobalEvent:${it.toString()}")
                onGobalEvent(it, navigator)
                when (it) {
                    is GobalEvent.ShowToast -> {
                        show.value = true
                        toataData = it.data
                    }
                }
            }
        }
        CreateContent(navigator)
        toast(show, modifier = Modifier, toataData)
    }

    @Composable
    abstract fun CreateContent(nav: Navigator)


    @Composable
    fun toast(state: MutableState<Boolean>, modifier: Modifier, data: String) {
        val density = LocalDensity.current
        AnimatedVisibility(state.value, enter = slideInVertically {
            //从顶部-200dp的位置开始滑入
            with(density) { -200.dp.roundToPx() }
        } + expandHorizontally(
            //展开位置
            expandFrom = Alignment.End
        ) + fadeIn(
            //从初始透明度0.3f开始淡入
            initialAlpha = 0.3f
        ), exit = slideOutHorizontally()
                + shrinkHorizontally()
                + fadeOut()
        ) {
            ToastDialog(modifier = modifier, data)
            coroutineScope.launch {
                delay(1000)//toast定时
                state.value = false
            }
        }
    }

    abstract fun onGobalEvent(event: GobalEvent, nav: Navigator)
}