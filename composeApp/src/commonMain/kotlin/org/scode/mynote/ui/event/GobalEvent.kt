package org.scode.mynote.ui.event

interface GobalEvent {
    object Idel : GobalEvent//新增或修改
    data class EditNote(val id: Long) : GobalEvent//新增或修改
    data class ShowToast(val data: String) : GobalEvent//弹窗
}