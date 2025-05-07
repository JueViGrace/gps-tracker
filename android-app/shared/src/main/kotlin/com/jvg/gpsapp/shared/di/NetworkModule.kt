package com.jvg.gpsapp.shared.di

import com.jvg.gpsapp.api.auth.AuthClient
import com.jvg.gpsapp.api.auth.DefaultAuthClient
import dev.tmapps.konnection.Konnection
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun networkModule(): Module = module {
    single {
        Konnection.createInstance(
            context = get(),
            enableDebugLog = true,
        )
    }

    singleOf(::DefaultAuthClient) bind AuthClient::class
}
