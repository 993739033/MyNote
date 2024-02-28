package org.scode.mynote.net

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

//网络库设置
class NetClient {
    val httpClient by lazy{
        HttpClient(){
            defaultRequest {
                //baseUrl
                url("https://rickandmortyapi.com")
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v("net", null, message)
                    }
                }
                level = LogLevel.BODY
            }.also { Napier.base(DebugAntilog()) }

            install(HttpRequestRetry) {
                retryOnServerErrors(3)
                exponentialDelay()//指数间隔
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 1000
                requestTimeoutMillis = 1000
            }
        }
    }

}


expect fun getHttpFactory(): HttpClientEngineFactory<HttpClientEngineConfig>
