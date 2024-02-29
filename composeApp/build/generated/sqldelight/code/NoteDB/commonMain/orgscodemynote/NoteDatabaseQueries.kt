package orgscodemynote

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String
import org.scode.mynote.bean.ExtraData

public class NoteDatabaseQueries(
  driver: SqlDriver,
  private val NoteDataAdapter: NoteData.Adapter,
) : TransacterImpl(driver) {
  public fun <T : Any> queryAll(mapper: (
    id: Long,
    name: String,
    createTime: String,
    lastTime: String,
    noteType: ExtraData,
    noteData: String,
  ) -> T): Query<T> = Query(745_211_124, arrayOf("NoteData"), driver, "NoteDatabase.sq", "queryAll",
      "SELECT * FROM NoteData") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      NoteDataAdapter.noteTypeAdapter.decode(cursor.getString(4)!!),
      cursor.getString(5)!!
    )
  }

  public fun queryAll(): Query<NoteData> = queryAll { id, name, createTime, lastTime, noteType,
      noteData ->
    NoteData(
      id,
      name,
      createTime,
      lastTime,
      noteType,
      noteData
    )
  }

  public fun <T : Any> queryById(id: Long, mapper: (
    id: Long,
    name: String,
    createTime: String,
    lastTime: String,
    noteType: ExtraData,
    noteData: String,
  ) -> T): Query<T> = QueryByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      NoteDataAdapter.noteTypeAdapter.decode(cursor.getString(4)!!),
      cursor.getString(5)!!
    )
  }

  public fun queryById(id: Long): Query<NoteData> = queryById(id) { id_, name, createTime, lastTime,
      noteType, noteData ->
    NoteData(
      id_,
      name,
      createTime,
      lastTime,
      noteType,
      noteData
    )
  }

  public fun updateNote(
    name: String,
    createTime: String,
    lastTime: String,
    noteType: ExtraData,
    noteData: String,
    id: Long,
  ) {
    driver.execute(-1_211_654_442, """
        |UPDATE NoteData SET name = ?,createTime = ?,
        |lastTime = ?,noteType = ?,noteData = ? WHERE id = ?
        """.trimMargin(), 6) {
          bindString(0, name)
          bindString(1, createTime)
          bindString(2, lastTime)
          bindString(3, NoteDataAdapter.noteTypeAdapter.encode(noteType))
          bindString(4, noteData)
          bindLong(5, id)
        }
    notifyQueries(-1_211_654_442) { emit ->
      emit("NoteData")
    }
  }

  public fun createNote(
    name: String,
    createTime: String,
    lastTime: String,
    noteType: ExtraData,
    noteData: String,
  ) {
    driver.execute(453_490_057, """
        |INSERT INTO NoteData(name,createTime,lastTime,noteType,noteData)
        | VALUES(?,?,?,?,?)
        """.trimMargin(), 5) {
          bindString(0, name)
          bindString(1, createTime)
          bindString(2, lastTime)
          bindString(3, NoteDataAdapter.noteTypeAdapter.encode(noteType))
          bindString(4, noteData)
        }
    notifyQueries(453_490_057) { emit ->
      emit("NoteData")
    }
  }

  public fun removeAll() {
    driver.execute(421_527_714, """DELETE FROM NoteData""", 0)
    notifyQueries(421_527_714) { emit ->
      emit("NoteData")
    }
  }

  public fun removeNoteById(id: Long) {
    driver.execute(-1_480_005_501, """DELETE FROM NoteData WHERE id = ?""", 1) {
          bindLong(0, id)
        }
    notifyQueries(-1_480_005_501) { emit ->
      emit("NoteData")
    }
  }

  private inner class QueryByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("NoteData", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("NoteData", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_626_749_663, """SELECT * FROM NoteData WHERE id = ?""", mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "NoteDatabase.sq:queryById"
  }
}
