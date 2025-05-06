package es.red.tcd.ui.components.standard.layout.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LinearLoadingComponent(
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            modifier = loadingModifier,
        )
    }
}
