package com.jvg.gpsapp.app.di

import com.jvg.gpsapp.app.data.services.LocationService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun servicesModule(): Module = module {
    singleOf(::LocationService)
}
