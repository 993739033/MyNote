package org.scode.mynote.notedb

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlin.Unit
import org.scode.mynote.notedb.composeApp.newInstance
import org.scode.mynote.notedb.composeApp.schema
import orgscodemynote.NoteData
import orgscodemynote.NoteDatabaseQueries

public interface NoteDB : Transacter {
  public val noteDatabaseQueries: NoteDatabaseQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = NoteDB::class.schema

    public operator fun invoke(driver: SqlDriver, NoteDataAdapter: NoteData.Adapter): NoteDB =
        NoteDB::class.newInstance(driver, NoteDataAdapter)
  }
}
