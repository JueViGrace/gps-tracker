package com.jvg.gpsapp.home.data

import com.jvg.gpsapp.api.tracking.TrackingClient
import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.StandardRepository
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
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
        return startAuthenticatedFlow { session ->
            emit(RequestState.Success(emptyList()))
        }
    }

    override fun sendTracking(tracking: Tracking): Flow<RequestState<Unit>> {
        return startAuthenticatedFlow { session ->
//            when(val call = client.sendTracking(session.accessToken, tracking))
        }
    }
}
