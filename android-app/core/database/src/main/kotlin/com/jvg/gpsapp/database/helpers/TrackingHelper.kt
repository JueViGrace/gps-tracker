package com.jvg.gpsapp.database.helpers

import com.jvg.gpsapp.database.driver.DriverFactory
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import migrations.Gps_tracking

interface TrackingHelper : DbHelper {
    suspend fun getTrackings(sessionId: String): List<Gps_tracking>
    suspend fun saveTrackings(trackings: List<Gps_tracking>)
}

class DefaultTrackingHelper(
    override val driver: DriverFactory,
    override val coroutineProvider: CoroutineProvider
) : TrackingHelper {
    override suspend fun getTrackings(sessionId: String): List<Gps_tracking> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrackings(trackings: List<Gps_tracking>) {
        TODO("Not yet implemented")
    }
}
