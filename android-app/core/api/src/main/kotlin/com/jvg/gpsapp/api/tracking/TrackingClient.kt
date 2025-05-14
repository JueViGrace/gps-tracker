package com.jvg.gpsapp.api.tracking

import com.jvg.gpsapp.api.ApiOperation
import com.jvg.gpsapp.api.client.StandardClient
import com.jvg.gpsapp.api.client.get
import com.jvg.gpsapp.api.client.post
import com.jvg.gpsapp.api.tracking.model.dto.TrackingDto
import com.jvg.gpsapp.api.tracking.model.response.TrackingResponse
import com.jvg.gpsapp.util.coroutines.CoroutineProvider

interface TrackingClient : StandardClient {
    suspend fun getTracking(token: String): ApiOperation<List<TrackingResponse>>
    suspend fun sendTracking(token: String, tracking: TrackingDto): ApiOperation<Unit>
}

class DefaultTrackingClient(
    override val coroutineProvider: CoroutineProvider
) : TrackingClient {
    override suspend fun getTracking(token: String): ApiOperation<List<TrackingResponse>> {
        return get(
            urlString = "/tracking",
            headers = mapOf("Authorization" to "Bearer $token")
        )
    }

    override suspend fun sendTracking(token: String, tracking: TrackingDto): ApiOperation<Unit> {
        return post(
            urlString = "/tracking/register",
            headers = mapOf("Authorization" to "Bearer $token"),
            body = tracking
        )
    }
}
