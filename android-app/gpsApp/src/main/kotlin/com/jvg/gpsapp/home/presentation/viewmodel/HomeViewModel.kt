package com.jvg.gpsapp.home.presentation.viewmodel

import com.jvg.gpsapp.shared.presentation.viewmodel.BaseViewModel
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.Navigator

class HomeViewModel(
    override val messages: Messages,
    override val navigator: Navigator,
) : BaseViewModel(messages = messages, navigator = navigator)
