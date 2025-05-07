package com.jvg.gpsapp.ui.components.standard.layout.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun CircularLoadingComponent(
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    width: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = loadingModifier,
            strokeWidth = width
        )
    }
}
