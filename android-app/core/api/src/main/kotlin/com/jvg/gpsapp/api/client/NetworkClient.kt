package com.jvg.gpsapp.api.client

import com.jvg.gpsapp.api.APIResponse
import com.jvg.gpsapp.api.NetworkRequestResult
import com.jvg.gpsapp.util.Dates
import com.jvg.gpsapp.util.Dates.formatDate
import com.jvg.gpsapp.util.Logs
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

interface NetworkClient {
    val tag: String
        get() = this::class.simpleName ?: "NetworkClient"

    val coroutineProvider: CoroutineProvider
    val coroutineContext: CoroutineContext
        get() = coroutineProvider.ioDispatcher
    val scope: CoroutineScope
        get() = coroutineProvider.createScope(coroutineContext)

    fun client(baseUrl: String? = null): HttpClient

    suspend fun call(
        method: HttpMethod,
        baseUrl: String?,
        urlString: String,
        body: Any?,
        headers: Map<String, String>,
        contentType: ContentType,
    ): NetworkRequestResult

    suspend fun get(
        baseUrl: String? = null,
        urlString: String,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): NetworkRequestResult {
        return call(
            method = HttpMethod.Get,
            baseUrl = baseUrl,
            urlString = urlString,
            body = null,
            headers = headers,
            contentType = contentType
        )
    }

    suspend fun post(
        baseUrl: String? = null,
        urlString: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): NetworkRequestResult {
        return call(
            method = HttpMethod.Post,
            baseUrl = baseUrl,
            urlString = urlString,
            body = body,
            headers = headers,
            contentType = contentType
        )
    }

    suspend fun put(
        baseUrl: String? = null,
        urlString: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): NetworkRequestResult {
        return call(
            method = HttpMethod.Put,
            baseUrl = baseUrl,
            urlString = urlString,
            body = body,
            headers = headers,
            contentType = contentType
        )
    }

    suspend fun patch(
        baseUrl: String? = null,
        urlString: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): NetworkRequestResult {
        return call(
            method = HttpMethod.Patch,
            baseUrl = baseUrl,
            urlString = urlString,
            body = body,
            headers = headers,
            contentType = contentType
        )
    }

    suspend fun delete(
        baseUrl: String? = null,
        urlString: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): NetworkRequestResult {
        return call(
            method = HttpMethod.Delete,
            baseUrl = baseUrl,
            urlString = urlString,
            body = body,
            headers = headers,
            contentType = contentType
        )
    }

    companion object {
        var BASE_URL: String = ""
        const val TIMEOUT: Long = 120_000
    }
}

internal fun URLBuilder.setupUrl(urlString: String) {
    val url = "/api$urlString"
    if (url.contains("?")) {
        val path: List<String> = url.split("?")

        path(path[0])
        var pair: List<String>
        if (path[1].contains("&")) {
            path[1].split("&").forEach { parameter ->
                pair = parameter.split("=")
                parameters.append(pair[0], pair[1])
            }
        } else {
            pair = path[1].split("=")
            parameters.append(pair[0], pair[1])
        }
    } else {
        path(url)
    }
}

interface StandardClient : NetworkClient {
    override fun client(baseUrl: String?): HttpClient = HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL

            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NetworkClient.TIMEOUT
            connectTimeoutMillis = NetworkClient.TIMEOUT
            socketTimeoutMillis = NetworkClient.TIMEOUT
        }

        install(ResponseObserver) {
            onResponse { response ->
                Logs.info(tag = tag, msg = "HTTP response: $response")
                Logs.info(tag = tag, msg = "HTTP status: ${response.status.value}")
                Logs.info(tag = tag, msg = "HTTP description: ${response.status.description}")
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = true
                }
            )
        }

        defaultRequest {
            url(urlString = baseUrl ?: NetworkClient.BASE_URL)
        }
    }

    override suspend fun call(
        method: HttpMethod,
        baseUrl: String?,
        urlString: String,
        body: Any?,
        headers: Map<String, String>,
        contentType: ContentType,
    ): NetworkRequestResult {
        return try {
            scope.coroutineContext.ensureActive()
            val response: HttpResponse = client(baseUrl).request {
                this.method = method
                url { url ->
                    url.setupUrl(urlString)
                }
                contentType(contentType)
                headers.forEach { (key: String, value: String) ->
                    headers {
                        append(key, value)
                    }
                }
                if (body != null) setBody(body)
            }

            when (response.status) {
                HttpStatusCode.OK,
                HttpStatusCode.Created,
                HttpStatusCode.Accepted,
                HttpStatusCode.NoContent -> {
                    NetworkRequestResult.Success(
                        response = APIResponse(
                            status = response.status.value,
                            description = response.status.description,
                            data = response,
                            message = "Success",
                            time = Dates.currentTime.formatDate(),
                        )
                    )
                }

                else -> {
                    NetworkRequestResult.Failure(
                        errorResponse = APIResponse(
                            status = HttpStatusCode.InternalServerError.value,
                            description = HttpStatusCode.InternalServerError.description,
                            data = response,
                            message = "Error",
                            time = Dates.currentTime.formatDate(),
                        ),
                    )
                }
            }
        } catch (e: Exception) {
            Logs.error(tag = tag, msg = "Call error:", tr = e)
            NetworkRequestResult.Failure(
                errorResponse = APIResponse(
                    status = HttpStatusCode.InternalServerError.value,
                    description = HttpStatusCode.InternalServerError.description,
                    data = null,
                    message = e.message ?: "",
                    time = Dates.currentTime.formatDate(),
                ),
            )
        }
    }
}
