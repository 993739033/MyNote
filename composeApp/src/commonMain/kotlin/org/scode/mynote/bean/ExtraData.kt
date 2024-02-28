package org.scode.mynote.bean

import kotlinx.serialization.Serializable

@Serializable
data class ExtraData(val name:String = "",val time:String = "")