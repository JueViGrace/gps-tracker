package com.jvg.gpsapp.auth.data

import com.jvg.gpsapp.api.ApiOperation
import com.jvg.gpsapp.api.auth.AuthClient
import com.jvg.gpsapp.api.auth.model.AuthResponse
import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.StandardRepository
import com.jvg.gpsapp.shared.data.mappers.auth.toSession
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.state.ResponseMessage
import com.jvg.gpsapp.util.Logs
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import kotlinx.coroutines.flow.Flow

interface AuthRepository : StandardRepository {
    fun activeSession(): Flow<RequestState<Boolean>>
    fun refresh(): Flow<RequestState<Unit>>
    fun login(): Flow<RequestState<Unit>>
}

class DefaultAuthRepository(
    private val authClient: AuthClient,
    override val authHelper: AuthHelper,
    override val coroutineProvider: CoroutineProvider,
) : AuthRepository {
    override fun activeSession(): Flow<RequestState<Boolean>> = startAuthenticatedFlow { session ->
        emit(RequestState.Success(true))
    }

    override fun refresh(): Flow<RequestState<Unit>> = startAuthenticatedFlow { session ->
        when (val call: ApiOperation<AuthResponse> = authClient.refresh(session.refreshToken)) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(call.error)
                )
            }
            is ApiOperation.Success -> {
                val data: AuthResponse = call.value.data
                    ?: return@startAuthenticatedFlow emit(
                        RequestState.Error(
                            ResponseMessage(
                                message = "Data is null"
                            )
                        )
                    )

                updateAuthentication(data.toSession().copy(active = true))

                emit(RequestState.Success(Unit))
            }
        }
    }

    override fun login(): Flow<RequestState<Unit>> = startFlow {
        when (val call: ApiOperation<AuthResponse> = authClient.login()) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(call.error)
                )
            }
            is ApiOperation.Success -> {
                val data: AuthResponse = call.value.data
                    ?: return@startFlow emit(
                        RequestState.Error(
                            ResponseMessage(
                                message = "Data is null"
                            )
                        )
                    )
                updateAuthentication(data.toSession().copy(active = true))
                emit(RequestState.Success(Unit))
            }
        }
    }
}
