package com.jvg.gpsapp

import android.app.Application
import com.jvg.gpsapp.app.di.appModule
import com.jvg.gpsapp.di.KoinBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication

class GPSApp : Application() {
    override fun onCreate() {
        super.onCreate()

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
