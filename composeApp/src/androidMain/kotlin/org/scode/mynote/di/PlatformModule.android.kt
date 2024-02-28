package org.scode.mynote.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.scode.mynote.db.sqldelight.DatabaseDriverFactory

actual fun platformModule(): Module =module{
    single { DatabaseDriverFactory(get()) }
}