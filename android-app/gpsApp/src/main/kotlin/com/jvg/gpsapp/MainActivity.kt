package com.jvg.gpsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.jvg.gpsapp.app.presentation.ui.screens.App
import es.red.tcd.ui.theme.AppTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KoinContext {
                AppTheme {
                    Surface {
                        App()
                    }
                }
            }
        }
    }
}
