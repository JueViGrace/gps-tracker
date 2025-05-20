package com.jvg.gpsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jvg.gpsapp.app.data.services.LocationWorker
import com.jvg.gpsapp.app.presentation.ui.screens.App
import es.red.tcd.ui.theme.AppTheme
import org.koin.compose.KoinContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KoinContext {
                AppTheme {
                    LaunchedEffect(Unit) {
                        val request = PeriodicWorkRequestBuilder<LocationWorker>(
                            repeatInterval = 15,
                            repeatIntervalTimeUnit = TimeUnit.MINUTES,
                        )
                            .setInitialDelay(Duration.parse("5s").toJavaDuration())
                            .setConstraints(Constraints.NONE)
                            .setBackoffCriteria(
                                backoffPolicy = BackoffPolicy.LINEAR,
                                backoffDelay = 15,
                                timeUnit = TimeUnit.SECONDS
                            )
                            .build()
                        val workManager = WorkManager.getInstance(applicationContext)
                        workManager.enqueueUniquePeriodicWork(
                            uniqueWorkName = "LocationWorker",
                            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
                            request = request
                        )
                    }

                    Surface {
                        App()
                    }
                }
            }
        }
    }
}
