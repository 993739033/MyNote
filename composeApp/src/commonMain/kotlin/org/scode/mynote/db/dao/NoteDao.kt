package org.scode.mynote.db.dao

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.company.app.utils.Log
import org.scode.mynote.bean.ExtraData
import org.scode.mynote.bean.NoteDataBean
import org.scode.mynote.db.DBManager
import orgscodemynote.NoteData

class NoteDao(private val dbManager: DBManager) {
    init {
        Log.e("NoteDao init")
    }

    suspend fun update(noteDataBean: NoteDataBean) {
        dbManager.invoke {
            it.noteDatabaseQueries.updateNote(
                noteDataBean.name, noteDataBean.createTime,
                noteDataBean.lastTime, noteDataBean.noteType, noteDataBean.noteData,
                noteDataBean.id
            )
        }
    }


    suspend fun create(noteDataBean: NoteDataBean) {
        dbManager.invoke {
            it.noteDatabaseQueries.createNote(
                noteDataBean.name, noteDataBean.createTime,
                noteDataBean.lastTime, noteDataBean.noteType, noteDataBean.noteData
            )
        }
    }

    suspend fun deleteAll() {
        dbManager.invoke {
            it.noteDatabaseQueries.removeAll()
        }
    }

    suspend fun deleteByID(id: Long) {
        dbManager.invoke {
            it.noteDatabaseQueries.removeNoteById(id)
        }
    }


    suspend fun queryAll(): Flow<List<NoteDataBean>> {
        return dbManager.invoke {
            it.noteDatabaseQueries
                .queryAll(::mapNoteDataBean)
                .asFlow()
                .map {
                    it.executeAsList()
                }
        }
    }

    suspend fun queryByID(id: Long): NoteDataBean {
        return dbManager.invoke {
            it.noteDatabaseQueries.queryById(id, ::mapNoteDataBean).executeAsOne()
        }
    }

    private fun mapNoteDataBean(
        id: Long, name: String, createTime: String,
        lastTime: String, noteType: ExtraData, data: String
    ): NoteDataBean = NoteDataBean(id, name, createTime, lastTime, noteType, data)
}