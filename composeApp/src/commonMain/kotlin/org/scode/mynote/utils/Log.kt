package org.company.app.utils

import io.github.aakira.napier.Napier

object Log {
    val logLevel: Int = 0
    val logTag = "app"

    fun v(data: String) {
        if (logLevel <= 0) {
            Napier.v(tag = logTag, message = data)
        }
    }

    fun i(data: String) {
        if (logLevel <= 1) {
            Napier.i(tag = logTag, message = data)
        }
    }

    fun d(data: String) {
        if (logLevel <= 2) {
            Napier.d(tag = logTag, message = data)
        }
    }

    fun w(data: String) {
        if (logLevel <= 3) {
            Napier.w(tag = logTag, message = data)
        }
    }

    fun e(data: String) {
        if (logLevel <= 4) {
            Napier.e(tag = logTag, message = data)
        }
    }
}