package com.jvg.gpsapp.auth.data

import com.jvg.gpsapp.api.auth.AuthClient
import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.StandardRepository
import com.jvg.gpsapp.shared.data.mappers.auth.toDb
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface AuthRepository : StandardRepository {
    fun refresh(): Flow<RequestState<Unit>>
    fun login(): Flow<RequestState<Unit>>
    fun cleanSession()
}

class DefaultAuthRepository(
    private val authClient: AuthClient,
    override val authHelper: AuthHelper,
    override val coroutineProvider: CoroutineProvider,
) : AuthRepository {
    override fun refresh(): Flow<RequestState<Unit>> {
        return startAuthenticatedNetworkRequest(
            call = { session ->
                authClient.refresh(session.refreshToken)
            }
        ) { _, data ->
            scope.launch {
                authHelper.updateSession(data.toDb().copy(active = true))
            }

            emit(RequestState.Success(Unit))
        }
    }

    override fun login(): Flow<RequestState<Unit>> {
        return startNetworkRequest(
            call = authClient::login
        ) { data ->
            scope.launch {
                authHelper.createSession(data.toDb().copy(active = true))
            }

            emit(RequestState.Success(Unit))
        }
    }

    override fun cleanSession() {
        scope.launch { authHelper.deleteSession() }
    }
}
