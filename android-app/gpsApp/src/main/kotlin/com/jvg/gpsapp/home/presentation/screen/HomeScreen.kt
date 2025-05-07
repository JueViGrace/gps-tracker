package com.jvg.gpsapp.home.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jvg.gpsapp.ui.components.standard.display.TextComponent

@Composable
fun HomeScreen() {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(text = "Home")
        }
    }
}
