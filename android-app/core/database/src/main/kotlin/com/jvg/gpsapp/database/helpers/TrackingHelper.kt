package com.jvg.gpsapp.database.helpers

import com.jvg.gpsapp.database.driver.DriverFactory
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import migrations.Gps_tracking

interface TrackingHelper : DbHelper {
    suspend fun getTrackings(sessionId: String): Flow<List<Gps_tracking>>
    suspend fun saveTrackings(trackings: List<Gps_tracking>)
}

class DefaultTrackingHelper(
    override val driver: DriverFactory,
    override val coroutineProvider: CoroutineProvider
) : TrackingHelper {
    override suspend fun getTrackings(sessionId: String): Flow<List<Gps_tracking>> {
        return withDatabase { db ->
            executeListAsFlow(db.trackingQueries.findTrackings(sessionId))
        } ?: emptyFlow()
    }

    override suspend fun saveTrackings(trackings: List<Gps_tracking>) {
        withDatabase { db ->
            db.transaction {
                scope.launch {
                    trackings.forEach { tracking ->
                        async {
                            db.trackingQueries.saveTrackings(tracking)
                        }
                    }
                }
            }
        }
    }
}
