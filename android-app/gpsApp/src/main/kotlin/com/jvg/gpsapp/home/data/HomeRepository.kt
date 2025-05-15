package com.jvg.gpsapp.home.data

import com.jvg.gpsapp.api.tracking.TrackingClient
import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.StandardRepository
import com.jvg.gpsapp.shared.data.mappers.tracking.toDto
import com.jvg.gpsapp.shared.data.mappers.tracking.toTracking
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.util.Logs
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow

interface HomeRepository : StandardRepository {
    fun getLocations(): Flow<RequestState<List<Tracking>>>
    fun sendTracking(tracking: Tracking): Flow<RequestState<Unit>>
}

class DefaultHomeRepository(
    private val client: TrackingClient,
    override val authHelper: AuthHelper,
    override val coroutineProvider: CoroutineProvider,
) : HomeRepository {
    override fun getLocations(): Flow<RequestState<List<Tracking>>> {
        return startAuthenticatedNetworkRequest(
            call = { session ->
                client.getTracking(session.accessToken)
            }
        ) { _, data ->
            emit(
                RequestState.Success(
                    data = data.map { tracking ->
                        scope.async { tracking.toTracking() }
                    }.awaitAll()
                )
            )
        }
    }

    override fun sendTracking(tracking: Tracking): Flow<RequestState<Unit>> {
        return startAuthenticatedNetworkRequest(
            call = { session ->
                client.sendTracking(session.accessToken, tracking.toDto())
            }
        ) { _, data ->
            Logs.info(tag, "Tracking sent: $data")
            emit(RequestState.Success(Unit))
        }
    }
}
