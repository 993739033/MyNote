package org.scode.mynote.notedb.composeApp

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass
import org.scode.mynote.notedb.NoteDB
import orgscodemynote.NoteData
import orgscodemynote.NoteDatabaseQueries

internal val KClass<NoteDB>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = NoteDBImpl.Schema

internal fun KClass<NoteDB>.newInstance(driver: SqlDriver, NoteDataAdapter: NoteData.Adapter):
    NoteDB = NoteDBImpl(driver, NoteDataAdapter)

private class NoteDBImpl(
  driver: SqlDriver,
  NoteDataAdapter: NoteData.Adapter,
) : TransacterImpl(driver), NoteDB {
  override val noteDatabaseQueries: NoteDatabaseQueries = NoteDatabaseQueries(driver,
      NoteDataAdapter)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS NoteData (
          |    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,
          |    name TEXT NOT NULL,
          |    createTime TEXT NOT NULL,
          |    lastTime TEXT NOT NULL,
          |    noteType TEXT NOT NULL,
          |    noteData TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
