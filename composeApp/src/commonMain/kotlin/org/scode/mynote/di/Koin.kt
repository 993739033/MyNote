package org.scode.mynote.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import org.company.app.utils.Log
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.scode.mynote.db.DBManager
import org.scode.mynote.db.dao.NoteDao
import org.scode.mynote.net.NetClient
import org.scode.mynote.net.NoteApi
import org.scode.mynote.repository.NoteRepository
import org.scode.mynote.ui.model.HistoryUseCase
import org.scode.mynote.ui.screen.detail.DetailModel
import org.scode.mynote.ui.screen.history.HistoryListModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            dispatcherModule,
            repositoryModule,
            useCasesModule,
            viewModelModule,
            ktorModule,
            sqlDelightMoudle,
            platformModule()
        )
    }
}

val ktorModule = module {
    single {
        NetClient()
    }
    single { NoteApi(get()) }
    single { "https://rickandmortyapi.com" }
}


val sqlDelightMoudle = module {
    single { DBManager(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.IO }

//    factory {
//        CoroutineScope(SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
//            Log.v("${throwable.message}")
//        })
//    }
}

val viewModelModule = module {
    factory { HistoryListModel(get()) }
    factory { DetailModel(get()) }
}

val useCasesModule = module {
    factory { HistoryUseCase(get(), get()) }
}

val repositoryModule = module {
    single { NoteDao(get()) }
    single { NoteApi(get()) }
    single { NoteRepository(get(), get()) }
}