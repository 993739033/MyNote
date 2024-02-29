package org.scode.mynote.ui.screen.detail

import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.company.app.utils.Log
import org.scode.mynote.base.state.ResourceUiState
import org.scode.mynote.bean.NoteDataBean
import org.scode.mynote.ui.model.HistoryUseCase
import org.scode.mynote.ui.screen.history.HistoryContract

class DetailModel(
    private val historyUseCase: HistoryUseCase
) : BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>() {

    init {
        Log.e("DetailModel init")
    }

    override fun createInitialState(): DetailContract.State {
        return DetailContract.State(
            note = ResourceUiState.Idle
        )
    }

    override fun handleEvent(event: DetailContract.Event) {
        when (event) {
            is DetailContract.Event.onSaveNote -> {
                if (event.note.id == -1L) {
                    createNote(event.note)
                } else {
                    saveNote(event.note)
                }
            }

            is DetailContract.Event.refreshData -> getNoteData(event.id)
            is DetailContract.Event.onDeleteNote -> {
                deleteNote(event.id)
            }
        }
    }

    private fun getNoteData(id: Long) {
        if (id == -1L || id == 0L) {
            setState { copy(note = ResourceUiState.Success(NoteDataBean())) }
            return
        }
        setState { copy(note = ResourceUiState.Loading) }
        coroutineScope.launch {
            historyUseCase.getHistoryDetailData(id)
                .let {
                    it.onSuccess {
                        setState {
                            copy(
                                note = if (it == null) {
                                    ResourceUiState.Empty
                                } else {
                                    ResourceUiState.Success(it)
                                }
                            )
                        }
                    }.onFailure {
                        println(it.message + " " + it.cause)
                        setState { copy(note = ResourceUiState.Error(it.message)) }
                    }
                }
        }
    }

    //保存修改
    private fun saveNote(note: NoteDataBean) {
        coroutineScope.launch {
            historyUseCase.updateHistoryData(note)
                .onSuccess {
                    setEffect { DetailContract.Effect.showToast("Title:${note.name} 数据保存成功!") }
                }
                .onFailure {
                    println(it.message + " " + it.cause)
                }
        }
    }

    //新建保存
    private fun createNote(note: NoteDataBean) {
        coroutineScope.launch {
            historyUseCase.createHistoryData(note)
                .onSuccess {
                    setEffect { DetailContract.Effect.showToast("Title:${note.name}\n 数据新建成功!") }
                    coroutineScope.launch {
                        delay(800)
                        setEffect { DetailContract.Effect.back }
                    }
                }
                .onFailure {
                    println(it.message + " " + it.cause)
                }
        }
    }

    //数据删除
    private fun deleteNote(id: Long) {
        coroutineScope.launch {
            historyUseCase.deleteDetailData(id)
                .onSuccess {
                    setEffect { DetailContract.Effect.showToast("数据删除成功!") }
                    delay(1000)
                    setEffect { DetailContract.Effect.back }
                }
                .onFailure {
                    println(it.message + " " + it.cause)
                }
        }
    }
}