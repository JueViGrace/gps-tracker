package com.jvg.gpsapp.shared.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun sharedModule(): Module = module {
    includes(
        networkModule(),
        uiModule(),
        databaseModule(),
        coroutineModule(),
    )
}
