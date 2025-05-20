package com.jvg.gpsapp.app.data.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationService(
    private val context: Context,
) {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @RequiresPermission(
        anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
    )
    suspend fun getUserLocation(): Location? {
        if (!checkRequirementsForLocation()) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete && isSuccessful) {
                    cont.resume(result) { cause, _, _ -> }
                    return@suspendCancellableCoroutine
                }
                if (isComplete && !isSuccessful) {
                    cont.resume(null) { cause, _, _ -> }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) { cause, _, _ -> }
                }
                addOnFailureListener {
                    cont.resume(null) { cause, _, _ -> }
                }
                addOnCanceledListener {
                    cont.resume(null) { cause, _, _ -> }
                }
            }
        }
    }

    private fun checkRequirementsForLocation(): Boolean {
        return permissionsGranted() && isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun permissionsGranted(): Boolean {
        val finePermission =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermission =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        return finePermission == PackageManager.PERMISSION_GRANTED &&
            coarsePermission == PackageManager.PERMISSION_GRANTED
    }
}
