package org.scode.mynote.db.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.scode.mynote.notedb.NoteDB

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return JdbcSqliteDriver("jdbc:sqlite:note.db").apply {
            NoteDB.Schema.create(this)
        }
    }
}