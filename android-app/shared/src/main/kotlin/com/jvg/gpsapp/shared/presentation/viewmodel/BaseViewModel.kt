package com.jvg.gpsapp.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.Navigator
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected open val messages: Messages,
    protected open val navigator: Navigator,
) : ViewModel() {
    protected val tag = this::class.simpleName ?: "BaseViewModel"

    protected fun showMessage(message: Int, description: String? = null) {
        viewModelScope.launch {
            messages.sendMessage(message, description)
        }
    }
}
