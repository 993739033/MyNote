package org.scode.mynote.net

import io.ktor.client.*
import io.ktor.client.engine.*

actual fun getHttpFactory(): HttpClientEngineFactory<HttpClientEngineConfig> {
    return HttpClient()
}