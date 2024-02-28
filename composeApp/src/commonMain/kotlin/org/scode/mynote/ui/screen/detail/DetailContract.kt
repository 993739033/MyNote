package org.scode.mynote.ui.screen.detail

import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState
import org.scode.mynote.base.state.ResourceUiState
import org.scode.mynote.bean.NoteDataBean

interface DetailContract {
    //view -> model
    sealed interface Event : UiEvent {
        data class refreshData(val id: Long) : Event
        data class onSaveNote(val note: NoteDataBean) : Event
        data class onDeleteNote(val id: Long) : Event
    }

    //model -> view
    data class State(val note: ResourceUiState<NoteDataBean>) : UiState

    //effect 类似于state 但更简便 不需要状态维持 在event中可用于回调view方法
    //model -> view
    sealed interface Effect : UiEffect {
        object back : Effect
        data class showToast(val data: String) : Effect
    }
}