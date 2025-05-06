package es.red.tcd.app.presentation.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.red.tcd.app.presentation.state.AppState
import es.red.tcd.app.presentation.state.UpdateState
import es.red.tcd.resources.R
import es.red.tcd.ui.components.standard.display.ImageComponent

@Composable
fun SplashScreen(
    state: AppState,
    painter: Painter = painterResource(id = R.drawable.),
    onRender: (UpdateState) -> Unit
) {
    LaunchedEffect(state.updateState) {
        onRender(state.updateState)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(
            modifier = Modifier.fillMaxWidth(0.5f)
                .background(
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.inverseSurface
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isSystemInDarkTheme()) {
                        Color.Transparent
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    shape = CircleShape
                )
                .padding(4.dp),
            painter = painter,
            contentDescription = stringResource(id = R.string.logo)
        )
    }
}
