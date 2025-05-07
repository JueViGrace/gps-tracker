package com.jvg.gpsapp.ui.components.standard.display

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.DigitContainer(
    isMasked: Boolean = true,
    isFocused: Boolean,
    digit: String,
    mask: @Composable (() -> Unit)? = null,
    textStyle: TextStyle,
    isError: Boolean = false,
) {
    val animatedColor by animateColorAsState(
        targetValue = when {
            isError -> MaterialTheme.colorScheme.error
            isFocused -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline
        },
        label = "Border Color",
        animationSpec = tween(durationMillis = 200)
    )

    val animatedBorderWidth by animateDpAsState(
        targetValue = when {
            isError -> 2.dp
            isFocused -> 2.dp
            else -> 1.dp
        },
        label = "BorderWidth",
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = animatedBorderWidth,
                color = animatedColor,
                shape = RoundedCornerShape(14.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isMasked && digit.isNotBlank()) {
            mask?.invoke()
                ?: Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                )
        } else {
            TextComponent(
                text = digit,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 0.dp)
                    .wrapContentHeight(),
                fontSize = TextUnit.Unspecified,
                fontWeight = null,
                style = textStyle,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}
