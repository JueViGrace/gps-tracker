package com.jvg.gpsapp.shared.di

import com.jvg.gpsapp.ui.messages.DefaultMessages
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.DefaultNavigator
import com.jvg.gpsapp.ui.navigation.Navigator
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun uiModule(): Module = module {
    singleOf(::DefaultMessages) bind Messages::class

    singleOf(::DefaultNavigator) bind Navigator::class
}
