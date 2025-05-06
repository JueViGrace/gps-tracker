package com.jvg.gpsapp.ui.components.standard.buttons

import androidx.annotation.DrawableRes
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.jvg.gpsapp.resources.R
import es.red.tcd.ui.components.standard.icons.IconComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BackArrowButton(
    @DrawableRes
    icon: Int = R.drawable.ic_chevron_left,
    onClick: () -> Unit
) {
    var enabled by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    DisposableEffect(true) {
        onDispose {
            enabled = true
        }
    }

    LaunchedEffect(enabled) {
        if (!enabled) {
            scope.launch {
                delay(1000)
                enabled = true
            }
            onClick()
        }
    }

    IconButton(
        onClick = {
            enabled = false
        },
        enabled = enabled
    ) {
        IconComponent(
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.go_back)
        )
    }
}
