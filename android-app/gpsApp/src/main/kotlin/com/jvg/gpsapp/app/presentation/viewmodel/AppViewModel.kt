package com.jvg.gpsapp.app.presentation.viewmodel

import com.jvg.gpsapp.shared.presentation.viewmodel.BaseViewModel
import com.jvg.gpsapp.ui.messages.Messages

class AppViewModel(
    override val messages: Messages
) : BaseViewModel(messages = messages) {
    fun invalidSession() {

    }
}
