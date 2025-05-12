package com.jvg.gpsapp.home.data

import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.shared.data.StandardRepository
import com.jvg.gpsapp.util.coroutines.CoroutineProvider

interface HomeRepository : StandardRepository {
    fun getLocations()
    fun sendLocations()
}

class DefaultHomeRepository(
    override val authHelper: AuthHelper,
    override val coroutineProvider: CoroutineProvider,
) : HomeRepository {
    override fun getLocations() {
        TODO("Not yet implemented")
    }

    override fun sendLocations() {
        TODO("Not yet implemented")
    }
}
