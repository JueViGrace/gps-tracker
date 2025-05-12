package com.jvg.gpsapp.auth.presentation.state

import androidx.annotation.StringRes

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val shouldRetry: Boolean = false,
    @StringRes
    val message: Int? = null,
)
