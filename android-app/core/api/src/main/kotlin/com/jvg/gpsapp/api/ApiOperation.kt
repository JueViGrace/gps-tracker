package com.jvg.gpsapp.api

import com.jvg.gpsapp.types.state.ResponseMessage

sealed interface ApiOperation<out T> {
    data class Success<T>(val value: APIResponse<T>) : ApiOperation<T>
    data class Failure(val error: ResponseMessage) : ApiOperation<Nothing>
}

suspend inline fun<reified T> ApiOperation<T>.onResult(
    onSuccess: suspend (APIResponse<T>) -> Unit,
    onFailure: suspend (ResponseMessage) -> Unit,
) {
    when (this) {
        is ApiOperation.Failure -> {
            onFailure(error)
        }
        is ApiOperation.Success -> {
            if (value.data == null) {
                onFailure(
                    ResponseMessage(
                        message = value.message,
                    )
                )
                return
            }
            onSuccess(value)
        }
    }
}
