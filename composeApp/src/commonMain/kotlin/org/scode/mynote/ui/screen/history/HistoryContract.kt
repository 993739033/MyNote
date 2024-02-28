package org.scode.mynote.ui.screen.history

import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState
import org.scode.mynote.base.state.ResourceUiState
import org.scode.mynote.bean.NoteDataBean

interface  HistoryContract {
    //view -> model
    sealed interface Event:UiEvent{
        object refreshData:Event
        data class onNoteClick(val note: NoteDataBean):Event
    }

    //model -> view
    data class State(val notes:ResourceUiState<List<NoteDataBean>>):UiState

    //effect 类似于state 但更简便 不需要状态维持 在event中可用于回调view方法
    //model -> view
    sealed interface Effect:UiEffect {
        data class NavigateToNoteDetail(val note:NoteDataBean):Effect
    }
}