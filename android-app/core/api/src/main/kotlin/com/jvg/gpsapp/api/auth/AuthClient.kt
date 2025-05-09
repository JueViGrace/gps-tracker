package com.jvg.gpsapp.api.auth

import com.jvg.gpsapp.api.ApiOperation
import com.jvg.gpsapp.api.auth.model.AuthResponse
import com.jvg.gpsapp.api.client.StandardClient
import com.jvg.gpsapp.api.client.post
import com.jvg.gpsapp.util.coroutines.CoroutineProvider

interface AuthClient : StandardClient {
    suspend fun login(): ApiOperation<AuthResponse>
    suspend fun refresh(refreshToken: String): ApiOperation<AuthResponse>
}

class DefaultAuthClient(
    override val coroutineProvider: CoroutineProvider
) : AuthClient {
    override suspend fun login(): ApiOperation<AuthResponse> {
        return post(
            urlString = "/auth/login",
        )
    }

    override suspend fun refresh(refreshToken: String): ApiOperation<AuthResponse> {
        return post(
            urlString = "/auth/refresh",
            body = mapOf("refreshToken" to refreshToken)
        )
    }
}
