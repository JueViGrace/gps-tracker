package com.jvg.gpsapp.home.data

import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.StandardRepository
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import kotlinx.coroutines.flow.Flow

interface HomeRepository : StandardRepository {
    fun getLocations(): Flow<RequestState<List<Tracking>>>
    fun sendLocations(): Flow<RequestState<Unit>>
}

class DefaultHomeRepository(
    override val authHelper: AuthHelper,
    override val coroutineProvider: CoroutineProvider,
) : HomeRepository {
    override fun getLocations(): Flow<RequestState<List<Tracking>>> =
        startAuthenticatedFlow { session ->
            emit(RequestState.Success(emptyList()))
        }

    override fun sendLocations(): Flow<RequestState<Unit>> = startAuthenticatedFlow { session ->
        emit(RequestState.Success(Unit))
    }
}
