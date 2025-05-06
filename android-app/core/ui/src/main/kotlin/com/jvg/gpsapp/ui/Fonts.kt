package com.jvg.gpsapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

object Fonts {
    val extraLargeTextStyle: TextStyle
        @Composable
        get() = when (getScreenSize()) {
            ScreenSize.Compact -> MaterialTheme.typography.headlineMedium
            ScreenSize.Medium -> MaterialTheme.typography.displaySmall
            ScreenSize.Large -> MaterialTheme.typography.displayMedium
        }

    val largeTextStyle: TextStyle
        @Composable
        get() = when (getScreenSize()) {
            ScreenSize.Compact -> MaterialTheme.typography.headlineSmall
            ScreenSize.Medium -> MaterialTheme.typography.headlineMedium
            ScreenSize.Large -> MaterialTheme.typography.headlineLarge
        }

    val mediumTextStyle: TextStyle
        @Composable
        get() = when (getScreenSize()) {
            ScreenSize.Compact -> MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            ScreenSize.Medium -> MaterialTheme.typography.titleLarge
            ScreenSize.Large -> MaterialTheme.typography.headlineSmall
        }

    val defaultTextStyle: TextStyle
        @Composable
        get() = when (getScreenSize()) {
            ScreenSize.Compact -> MaterialTheme.typography.bodyLarge
            ScreenSize.Medium -> MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            ScreenSize.Large -> MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp)
        }

    val smallTextStyle: TextStyle
        @Composable
        get() = when (getScreenSize()) {
            ScreenSize.Compact -> MaterialTheme.typography.bodyMedium
            ScreenSize.Medium -> MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
            ScreenSize.Large -> MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp)
        }

    val labelTextStyle: TextStyle
        @Composable
        get() = when (getScreenSize()) {
            ScreenSize.Compact -> MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
            ScreenSize.Medium -> MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp)
            ScreenSize.Large -> MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp)
        }
}
