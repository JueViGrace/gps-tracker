package es.red.tcd.ui.components.standard.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.ui.Fonts

@Composable
fun RowScope.FormItemTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    color: Color? = null,
) {
    Row(
        modifier = modifier.weight(1f).padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            modifier = Modifier.width(IntrinsicSize.Max),
            text = title,
            style = Fonts.mediumTextStyle,
            color = color ?: LocalContentColor.current,
            textAlign = TextAlign.Start,
            maxLines = 1,
        )
        value?.let { text ->
            TextComponent(
                modifier = Modifier.weight(1f),
                text = text,
                style = Fonts.mediumTextStyle,
                color = LocalContentColor.current.copy(alpha = 0.7f),
                textAlign = TextAlign.End,
                maxLines = 10,
            )
        }
    }
}
