package com.jvg.gpsapp

import android.app.Application
import com.jvg.gpsapp.api.client.NetworkClient
import com.jvg.gpsapp.app.di.appModule
import com.jvg.gpsapp.di.KoinBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication

class GPSApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        NetworkClient.BASE_URL = BuildConfig.BASE_URL

        KoinBuilder(koinApplication())
            .addConfig(appDeclaration = {
                androidLogger(
                    level = if (BuildConfig.DEBUG) {
                        Level.DEBUG
                    } else {
                        Level.NONE
                    }
                )
                androidContext(this@GPSApp)
            })
            .addModule(module = appModule())
            .build()
    }
}
