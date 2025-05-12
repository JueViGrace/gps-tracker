package com.jvg.gpsapp.home.di

import com.jvg.gpsapp.home.data.DefaultHomeRepository
import com.jvg.gpsapp.home.data.HomeRepository
import com.jvg.gpsapp.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun homeModule(): Module = module {
    singleOf(::DefaultHomeRepository) bind HomeRepository::class

    viewModelOf(::HomeViewModel)
}
