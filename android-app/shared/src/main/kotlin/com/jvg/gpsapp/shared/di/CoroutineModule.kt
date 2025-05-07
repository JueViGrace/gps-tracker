package com.jvg.gpsapp.shared.di

import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun coroutineModule(): Module = module {
    singleOf(::CoroutineProvider)
}
