package orgscodemynote

import app.cash.sqldelight.ColumnAdapter
import kotlin.Long
import kotlin.String
import org.scode.mynote.bean.ExtraData

public data class NoteData(
  public val id: Long,
  public val name: String,
  public val createTime: String,
  public val lastTime: String,
  public val noteType: ExtraData,
  public val noteData: String,
) {
  public class Adapter(
    public val noteTypeAdapter: ColumnAdapter<ExtraData, String>,
  )
}
