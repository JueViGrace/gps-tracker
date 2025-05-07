package com.jvg.gpsapp.auth.di

import com.jvg.gpsapp.auth.data.AuthRepository
import com.jvg.gpsapp.auth.data.DefaultAuthRepository
import com.jvg.gpsapp.auth.presentation.viewmodel.AuthViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthRepository) bind AuthRepository::class

    viewModelOf(::AuthViewModel)
}
