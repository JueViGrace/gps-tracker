package com.jvg.gpsapp.app.di

import com.jvg.gpsapp.auth.di.authModule
import com.jvg.gpsapp.home.di.homeModule
import com.jvg.gpsapp.shared.di.sharedModule
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module = module {
    includes(sharedModule(), authModule(), homeModule())
}
