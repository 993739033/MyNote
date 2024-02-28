package org.scode.mynote.bean

import kotlinx.serialization.Serializable

@Serializable
data class NoteDataBean(
    var id: Long=-1,
    var name: String = "",
    var createTime: String = "",
    var lastTime: String = "",
    var noteType: ExtraData = ExtraData(),
    var noteData: String = ""
)