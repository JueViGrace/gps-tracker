package com.jvg.gpsapp.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getScreenWidth(): Dp {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    return with(density) {
        windowInfo.containerSize.width.toDp()
    }
}

@Composable
fun getScreenHeight(): Dp {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    return with(density) {
        windowInfo.containerSize.height.toDp()
    }
}

@Composable
fun getScreenOrientation(): Orientation {
    var orientation by rememberSaveable { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    return if (orientation == Configuration.ORIENTATION_LANDSCAPE) Orientation.Landscape else Orientation.Portrait
}

enum class Orientation {
    Portrait,
    Landscape,
}

@Composable
fun getScreenSize(): ScreenSize {
    val width = getScreenWidth()
    val height = getScreenHeight()

    return when (getScreenOrientation()) {
        Orientation.Portrait -> {
            when {
                width < 600.dp ||
                    (height >= 480.dp && height < 900.dp) -> ScreenSize.Compact
                (width >= 600.dp && width < 840.dp) ||
                    (height >= 900.dp) -> ScreenSize.Medium
                width >= 840.dp || height > 900.dp -> ScreenSize.Large
                else -> ScreenSize.Large
            }
        }
        Orientation.Landscape -> {
            when {
                height < 480.dp || width <= 840.dp -> ScreenSize.Compact
                (height >= 480.dp && height < 900.dp) ||
                    width > 840.dp -> ScreenSize.Medium
                height >= 1200.dp || width > 840.dp -> ScreenSize.Large
                else -> ScreenSize.Large
            }
        }
    }
}

enum class ScreenSize {
    Compact,
    Medium,
    Large
}
