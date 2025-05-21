package com.jvg.gpsapp.app.data.services

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jvg.gpsapp.home.data.HomeRepository
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.tracking.Tracking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocationWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {
    private val locationService: LocationService by inject()
    private val homeRepository: HomeRepository by inject()

    override suspend fun doWork(): Result {
        return sendTracking()
    }

    @SuppressLint("MissingPermission")
    private suspend fun sendTracking(): Result {
        var result = Result.retry()
        val location = if (locationService.permissionsGranted()) {
            locationService.getUserLocation()
        } else {
            null
        } ?: return Result.retry()

        homeRepository.sendTracking(
            Tracking(
                latitude = location.latitude,
                longitude = location.longitude,
                altitude = location.altitude,
                time = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            )
        ).collect { postResult ->
            when (postResult) {
                is RequestState.Success -> {
                    homeRepository.getLocations().collect { locationsResult ->
                        if (locationsResult is RequestState.Success) {
                            println("Locations updated")
                            result = Result.success()
                        }
                        if (locationsResult is RequestState.Error) {
                            println("Error updating locations")
                            result = Result.retry()
                        }
                    }
                }

                is RequestState.Error -> {
                    result = Result.retry()
                }
                else -> {}
            }
        }
        return result
    }
}
