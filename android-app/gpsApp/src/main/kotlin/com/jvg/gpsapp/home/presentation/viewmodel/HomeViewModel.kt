package com.jvg.gpsapp.home.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.jvg.gpsapp.home.data.HomeRepository
import com.jvg.gpsapp.home.presentation.state.HomeState
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.shared.presentation.viewmodel.BaseViewModel
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val repository: HomeRepository,
    override val messages: Messages,
    override val navigator: Navigator,
) : BaseViewModel(messages = messages, navigator = navigator) {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTracking()
        }
    }

    suspend fun getTracking() {
        repository.getLocations().collect { result ->
            when (result) {
                is RequestState.Error -> {
                    _state.update { state ->
                        state.copy(
                            trackingLoading = false,
                            trackingList = emptyList(),
                            trackingError = true
                        )
                    }
                    showMessage(R.string.unexpected_error, result.error.message)
                }

                is RequestState.Success -> {
                    _state.update { state ->
                        state.copy(
                            trackingLoading = false,
                            trackingList = result.data,
                            trackingError = false,
                        )
                    }
                }

                else -> {
                    _state.update { state ->
                        state.copy(
                            trackingLoading = true,
                            trackingList = emptyList(),
                            trackingError = false
                        )
                    }
                }
            }
        }
    }

    fun updateLocation(location: Location?) {
        if (location != null) {
            _state.update { state ->
                state.copy(
                    tracking = Tracking(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        altitude = location.altitude,
                        time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
            }

            viewModelScope.launch {
                _state.value.tracking?.let { tracking ->
                    repository.sendTracking(tracking).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                showMessage(R.string.unexpected_error, result.error.message)
                            }

                            is RequestState.Success -> {
                                getTracking()
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
