package org.scode.mynote.utils

import kotlinx.datetime.*

object TimeUtil {

    //    yyyy-MM-dd'T'HH:mm:ss[.SSS]
    val formatPattern = "yyyy-MM-dd HH:mm"
    fun getNowTime(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        var date: String = now.let {
            "${it.date.month}/${it.date.dayOfMonth}  ${it.time.hour}:${it.time.minute}"
        }
        return date
    }
}