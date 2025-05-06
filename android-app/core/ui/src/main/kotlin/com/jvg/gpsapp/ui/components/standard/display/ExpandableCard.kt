package es.red.tcd.ui.components.standard.display

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.red.tcd.resources.R
import com.jvg.gpsapp.ui.Fonts
import es.red.tcd.ui.components.standard.icons.IconComponent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun<T> ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: Painter = painterResource(id = R.drawable.ic_chevron_down),
    expanded: Boolean = false,
    onExpandClick: () -> Unit,
    items: List<T> = emptyList(),
    expandedContent: @Composable (Int, T) -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        // Comment the following line if you want to make the card expand
        onClick = onExpandClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingIcon?.let { it() }

                TextComponent(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = Fonts.mediumTextStyle,
                    textAlign = TextAlign.Center
                )

                val rotation by animateFloatAsState(
                    targetValue = if (expanded) 180f else 0f,
                    label = "rotation"
                )

                IconComponent(
                    modifier = Modifier
                        .rotate(rotation),
                    painter = trailingIcon,
                    contentDescription = if (expanded) {
                        stringResource(id = R.string.show_less)
                    } else {
                        stringResource(id = R.string.show_more)
                    },
                    onClick = onExpandClick,
                )
            }

            AnimatedVisibility(visible = expanded) {
                FlowColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    maxLines = 1
                ) {
                    if (items.isNotEmpty()) {
                        for ((index, item) in items.withIndex()) {
                            expandedContent(index, item)
                        }
                    } else {
                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            TextComponent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                text = stringResource(id = R.string.no_items)
                            )
                        }
                    }
                }
            }
        }
    }
}
