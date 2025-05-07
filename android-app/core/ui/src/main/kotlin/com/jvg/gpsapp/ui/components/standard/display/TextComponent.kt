package com.jvg.gpsapp.ui.components.standard.display

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.jvg.gpsapp.ui.Fonts

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 2,
    color: Color = LocalContentColor.current,
    textDecoration: TextDecoration = TextDecoration.None,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle = Fonts.defaultTextStyle,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        maxLines = maxLines,
        color = color,
        modifier = modifier,
        textDecoration = textDecoration,
        softWrap = softWrap,
        overflow = overflow,
        style = style
    )
}
