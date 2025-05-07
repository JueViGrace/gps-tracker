package com.jvg.gpsapp.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.jvg.gpsapp.auth.data.AuthRepository
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.shared.presentation.viewmodel.BaseViewModel
import com.jvg.gpsapp.types.state.RequestState
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.Destination
import com.jvg.gpsapp.ui.navigation.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    override val messages: Messages,
    override val navigator: Navigator
) : BaseViewModel(messages = messages, navigator = navigator) {
    fun initSession() {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            authRepository.refresh().collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        // todo: update state to show message in ui
                        startSession()
                    }
                    is RequestState.Success -> {
                        // todo: update ui
                        navigateHome()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun startSession() {
        viewModelScope.launch {
            authRepository.login().collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        // todo: update state to show message in ui
                        // todo: retry logic?
                        messages.sendMessage(
                            message = R.string.unexpected_error,
                        )
                    }
                    is RequestState.Success -> {
                        // todo: update ui
                        navigateHome()
                    }
                    else -> {
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
