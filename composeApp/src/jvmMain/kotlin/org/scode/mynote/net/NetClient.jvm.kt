package org.scode.mynote.net

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual fun getHttpFactory(): HttpClientEngineFactory<HttpClientEngineConfig> {
    return OkHttp
}