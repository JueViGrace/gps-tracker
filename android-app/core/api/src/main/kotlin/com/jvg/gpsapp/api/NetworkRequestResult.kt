package com.jvg.gpsapp.api

import com.jvg.gpsapp.types.state.ResponseMessage
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

sealed interface NetworkRequestResult {
    data class Success(val response: APIResponse<HttpResponse>) : NetworkRequestResult
    data class Failure(val errorResponse: APIResponse<HttpResponse>) : NetworkRequestResult
}

suspend inline fun<reified T> NetworkRequestResult.toApiOperation(): ApiOperation<T> {
    return try {
        when (this) {
            is NetworkRequestResult.Failure -> {
                ApiOperation.Failure(
                    error = ResponseMessage(
                        message = errorResponse.message
                    )
                )
            }

            is NetworkRequestResult.Success -> {
                ApiOperation.Success(
                    value = APIResponse(
                        status = response.status,
                        description = response.description,
                        data = response.data?.body(),
                        message = response.message,
                        time = response.time
                    )
                )
            }
        }
    } catch (e: Exception) {
        ApiOperation.Failure(
            error = ResponseMessage(
                message = e.message
            )
        )
    }
}
