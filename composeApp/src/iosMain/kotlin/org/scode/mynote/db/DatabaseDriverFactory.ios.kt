package org.scode.mynote.db.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.scode.mynote.notedb.NoteDB

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(NoteDB.Schema, "note.db")
    }
}