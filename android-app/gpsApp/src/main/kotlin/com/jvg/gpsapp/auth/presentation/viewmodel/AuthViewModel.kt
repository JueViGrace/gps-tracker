package com.jvg.gpsapp.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.jvg.gpsapp.auth.data.AuthRepository
import com.jvg.gpsapp.auth.presentation.state.AuthState
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.shared.presentation.viewmodel.BaseViewModel
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.Destination
import com.jvg.gpsapp.ui.navigation.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    override val messages: Messages,
    override val navigator: Navigator
) : BaseViewModel(messages = messages, navigator = navigator) {
    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun initSession() {
        refresh()
    }

    private fun refresh() {
        _state.update { state ->
            state.copy(
                isLoading = true,
                message = R.string.checking_session,
                isAuthenticated = false,
            )
        }
        viewModelScope.launch {
            delay(1000)
            authRepository.refresh().collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        _state.update { state ->
                            state.copy(
                                message = R.string.no_session_found,
                                isAuthenticated = false,
                            )
                        }
                        delay(1000)
                        startSession()
                    }
                    is RequestState.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                message = R.string.session_started,
                                isAuthenticated = true,
                            )
                        }
                        navigateHome()
                    }
                    else -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                message = R.string.loading_server_session,
                                isAuthenticated = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun startSession() {
        _state.update { state ->
            state.copy(
                isLoading = true,
                message = R.string.starting_session,
                isAuthenticated = false,
            )
        }
        viewModelScope.launch {
            delay(1000)
            authRepository.login().collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        // todo: retry logic?
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                message = R.string.failed_to_start_session,
                                isAuthenticated = false
                            )
                        }
                        messages.sendMessage(
                            message = R.string.unexpected_error,
                            description = result.error.message
                        )
                    }
                    is RequestState.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                message = R.string.session_started,
                                isAuthenticated = true,
                            )
                        }
                        navigateHome()
                    }
                    else -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                message = R.string.loading_server_session,
                                isAuthenticated = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun navigateHome() {
        viewModelScope.launch {
            delay(1000)
            navigator.navigate(
                destination = Destination.Home,
                navOptions = navOptions {
                    launchSingleTop = true
                    popUpTo(Destination.Root)
                }
            )
        }
    }
}
