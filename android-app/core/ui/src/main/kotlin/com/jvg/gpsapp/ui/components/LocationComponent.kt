package com.jvg.gpsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.ui.Fonts
import com.jvg.gpsapp.ui.components.standard.display.TextComponent

@Composable
fun LocationComponent(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = title,
            style = Fonts.mediumTextStyle,
        )
        TextComponent(
            text = value,
        )
    }
}
