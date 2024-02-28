package org.scode.mynote.net

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.company.app.utils.Log
import org.scode.mynote.bean.Character
import org.scode.mynote.bean.CharacterApi

class NoteApi(val netClient:NetClient) {
    val httpClient = netClient.httpClient

    init {
        Log.e("NoteApi init")
    }

    suspend fun postTemp() {
//        val result = httpClient.post("https://www.acfun.cn/rest/pc-direct/new-danmaku/pollByPosition") {
//            setBody(FormDataContent(Parameters.build {
//                append("resourceId", videoId)
//                append("positionFromInclude", (page * 60000).toString())
//                append("positionToExclude", ((page + 1) * 60000).toString())
//                append("enableAdvanced", "false")
//            }))
//        }.body<VideoDanmaku?>()
    }


    suspend fun getTemp() {
//        val result = httpClient.get("https://live.acfun.cn/api/channel/list") {
//            parameter("count", pageSize)
//            parameter("pcursor", page)
//        }.body<LiveRoomList>().liveList
    }

    suspend fun getCharacter():List<Character>{
        return (httpClient.get("/api/character").body<CharacterApi>()).result
    }

}