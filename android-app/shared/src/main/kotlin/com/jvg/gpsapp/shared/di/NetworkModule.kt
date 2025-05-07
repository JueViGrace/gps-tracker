package com.jvg.gpsapp.shared.di

import dev.tmapps.konnection.Konnection
import org.koin.core.module.Module
import org.koin.dsl.module

fun networkModule(): Module = module {
    single {
        Konnection.createInstance(
            context = get(),
            enableDebugLog = true,
        )
    }
}
