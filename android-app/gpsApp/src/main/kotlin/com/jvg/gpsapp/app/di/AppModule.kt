package com.jvg.gpsapp.app.di

import com.jvg.gpsapp.shared.di.sharedModule
import com.jvg.gpsapp.app.presentation.viewmodel.AppViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun appModule(): Module = module {
    viewModelOf(::AppViewModel)

    includes(sharedModule())
}
