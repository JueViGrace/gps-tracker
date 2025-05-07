package com.jvg.gpsapp.shared.presentation.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.components.standard.display.ImageComponent

@Composable
fun SplashScreen(
    painter: Painter,
    onRender: () -> Unit
) {
    LaunchedEffect(true) {
        onRender()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(4.dp),
            painter = painter,
            contentDescription = stringResource(id = R.string.logo)
        )
    }
}
