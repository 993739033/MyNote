package org.scode.mynote.repository

import kotlinx.coroutines.flow.Flow
import org.company.app.utils.Log
import org.scode.mynote.bean.Character
import org.scode.mynote.bean.NoteDataBean
import org.scode.mynote.db.DBManager
import org.scode.mynote.db.dao.NoteDao
import org.scode.mynote.net.NoteApi

class NoteRepository(
    private val noteApi: NoteApi,
    private val noteDao: NoteDao
) {

    init {
        Log.e("NoteRepository init")
    }

    suspend fun getCharacter():List<Character>{
       return noteApi.getCharacter()
    }

    suspend fun getNoteList(): Flow<List<NoteDataBean>> {
        return noteDao.queryAll()
    }

    suspend fun getNote(id:Long):NoteDataBean{
        return noteDao.queryByID(id)
    }

    suspend fun updateNote(noteData:NoteDataBean) {
        noteDao.update(noteData)
    }

    suspend fun createNote(noteData:NoteDataBean) {
        noteDao.create(noteData)
    }

    suspend fun removeNote(id:Long) {
        noteDao.deleteByID(id)
    }
}