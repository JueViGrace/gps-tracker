package com.jvg.gpsapp.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvg.gpsapp.ui.messages.Messages
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected open val messages: Messages
) : ViewModel() {
    protected fun showMessage(message: Int, description: String? = null) {
        viewModelScope.launch {
            messages.sendMessage(message, description)
        }
    }
}
