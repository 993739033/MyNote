package org.scode.mynote.ui.screen.history

import cafe.adriel.voyager.core.model.screenModelScope
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.company.app.utils.Log
import org.scode.mynote.base.state.ResourceUiState
import org.scode.mynote.ui.model.HistoryUseCase

//note记录
class HistoryListModel(
    private val historyUseCase: HistoryUseCase
) : BaseViewModel<HistoryContract.Event, HistoryContract.State, HistoryContract.Effect>() {

    init {
        getNoteDatas()
        Log.e("HistoryListModel init")
    }

    override fun createInitialState(): HistoryContract.State {
        return HistoryContract.State(notes = ResourceUiState.Idle)
    }

    //view通过event调用model层请求数据
    //model层在这里处理数据
    override fun handleEvent(event: HistoryContract.Event) {
        when (event) {
            is HistoryContract.Event.onNoteClick -> setEffect { HistoryContract.Effect.NavigateToNoteDetail(event.note) }
            HistoryContract.Event.refreshData -> getNoteDatas()
        }
    }

    private fun getNoteDatas() {
        setState { copy(notes = ResourceUiState.Loading) }
        coroutineScope.launch {
            historyUseCase.getHistoryData()(Unit)
                .collect {
                    it.onSuccess {
                        setState {
                            copy(
                                notes = if (it.isEmpty()) {
                                    ResourceUiState.Empty
                                } else {
                                    ResourceUiState.Success(it)
                                }
                            )
                        }
                    }.onFailure {
                        println(it.message + " " + it.cause)
                        setState { copy(notes = ResourceUiState.Error(it.message)) }
                    }
                }
        }
    }

//    private fun insertNoteData(noteDataBean: NoteDataBean) {
//        screenModelScope.launch {
//            historyUseCase.insertHistoryData(noteDataBean)
//                .onSuccess {
//                    setState {
//                        copy(
//                            notes = if (it != null) {
//                                ResourceUiState.Empty
//                            } else {
//                                ResourceUiState.Success(it)
//                            }
//                        )
//                    }
//                }.onFailure {
//                    println("save note failed" + it.message + " " + it.cause)
//                    setState { copy(notes = ResourceUiState.Error(it.message)) }
//                }
//        }
//    }
//
//
//    private fun getNoteDetail(id: Long) {
//        setState { copy(notes = ResourceUiState.Loading) }
//        screenModelScope.launch {
//            historyUseCase.getHistoryDetailData(id)
//                .onSuccess {
//                    Log.e("save note success")
//                }.onFailure {
//                    println("save note failed" + it.message + " " + it.cause)
//                }
//        }
//    }
//
//
//    private fun deleteNoteData(id: Long) {
//        screenModelScope.launch {
//            historyUseCase.getHistoryDetailData(id)
//                .onSuccess {
//                    Log.e("delete note success")
//                }.onFailure {
//                    println("delete note failed" + it.message + " " + it.cause)
//                }
//        }
//    }


}