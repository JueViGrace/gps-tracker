package com.jvg.gpsapp.shared.data

import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.mappers.auth.toSession
import com.jvg.gpsapp.types.auth.Session
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.state.ResponseMessage
import com.jvg.gpsapp.util.Logs
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

interface Repository {
    val tag: String
        get() = this::class.simpleName ?: "Repository"

    val coroutineProvider: CoroutineProvider
    val coroutineContext: CoroutineContext
        get() = coroutineProvider.defaultDispatcher
    val scope: CoroutineScope
        get() = coroutineProvider.createScope(coroutineContext)
}

interface StandardRepository : Repository {
    val authHelper: AuthHelper

    fun <T> startFlow(
        block: suspend FlowCollector<RequestState<T>>.() -> Unit
    ): Flow<RequestState<T>> {
        return flow {
            emit(RequestState.Loading)
            try {
                block()
            } catch (e: Exception) {
                Logs.error(tag = tag, msg = "Start flow error:", tr = e)
                emit(
                    RequestState.Error(
                        error = ResponseMessage(
                            message = e.message,
                        ),
                    ),
                )
            }
        }.flowOn(coroutineContext)
    }

    fun <T> startAuthenticatedFlow(
        block: suspend FlowCollector<RequestState<T>>.(Session) -> Unit
    ): Flow<RequestState<T>> {
        return flow {
            emit(RequestState.Loading)
            try {
                val session: Session = authHelper.getSession()?.toSession()
                    ?: return@flow emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = "Session not found"
                            ),
                        ),
                    )
                block(session)
            } catch (e: Exception) {
                Logs.error(tag = tag, msg = "Start authenticated flow error:", tr = e)
                emit(
                    RequestState.Error(
                        ResponseMessage(
                            message = e.message,
                        ),
                    ),
                )
            }
        }.flowOn(coroutineContext)
    }
}
