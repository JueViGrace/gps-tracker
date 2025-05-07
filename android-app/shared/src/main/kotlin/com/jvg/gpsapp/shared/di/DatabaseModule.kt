package com.jvg.gpsapp.shared.di

import com.jvg.gpsapp.database.driver.DefaultDriverFactory
import com.jvg.gpsapp.database.driver.DriverFactory
import com.jvg.gpsapp.database.helpers.AuthHelper
import com.jvg.gpsapp.database.helpers.DefaultAuthHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun databaseModule(): Module = module {
    singleOf(::DefaultDriverFactory) bind DriverFactory::class

    singleOf(::DefaultAuthHelper) bind AuthHelper::class
}
