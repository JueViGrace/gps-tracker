package es.red.tcd.ui.components.standard.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundIcon(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    backgroundColor: Color = Color.Transparent,
    shape: Shape = CircleShape,
    painter: Painter,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        IconComponent(
            modifier = Modifier.padding(contentPadding).fillMaxSize(),
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
