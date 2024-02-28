package org.scode.mynote.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.scode.mynote.bean.ExtraData
import org.scode.mynote.db.sqldelight.DatabaseDriverFactory
import org.scode.mynote.notedb.NoteDB
import orgscodemynote.NoteData

class DBManager(private val driverFactory: DatabaseDriverFactory) {
    private var database: NoteDB? = null

    private val stringOfExtraDataAdapter = object : ColumnAdapter<ExtraData, String> {
        override fun decode(databaseValue: String): ExtraData {
            return Json.decodeFromString<ExtraData>(databaseValue)
        }

        override fun encode(v: ExtraData): String {
            return Json.encodeToString(v)
        }
    }

    private suspend fun initDatabase() {
        if (database == null) {
            database = NoteDB(driverFactory.createDriver(), NoteData.Adapter(stringOfExtraDataAdapter))
        }
    }

    suspend operator fun <R> invoke(block: suspend (NoteDB) -> R): R {
        initDatabase()
        return block(database!!)
    }
}