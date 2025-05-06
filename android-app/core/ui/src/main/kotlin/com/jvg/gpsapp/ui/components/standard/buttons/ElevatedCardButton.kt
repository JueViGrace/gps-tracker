package es.red.tcd.ui.components.standard.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.red.tcd.resources.R
import es.red.tcd.ui.components.standard.display.TextComponent
import es.red.tcd.ui.components.standard.icons.IconComponent

@Composable
fun StandardCardButton(
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    title: String,
    trailingIcon: Int? = null,
    onClick: () -> Unit
) {
    ElevatedCardButton(
        modifier = modifier,
        title = {
            TextComponent(
                modifier = Modifier.weight(1f),
                text = title,
                maxLines = 1
            )
        },
        leadingIcon = if (leadingIcon != null) {
            {
                IconComponent(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = title
                )
            }
        } else {
            null
        },
        trailingIcon = if (trailingIcon != null) {
            {
                IconComponent(
                    painter = painterResource(id = trailingIcon),
                    contentDescription = stringResource(id = R.string.go_forward)
                )
            }
        } else {
            null
        },
        onClick = onClick
    )
}

@Composable
fun ElevatedCardButton(
    modifier: Modifier = Modifier,
    title: @Composable RowScope.() -> Unit,
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    onClick: () -> Unit,
    colors: CardColors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
) {
    ElevatedCard(
        modifier = modifier,
        onClick = onClick,
        colors = colors
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { it() }
            title()
            trailingIcon?.let { it() }
        }
    }
}

@Composable
fun OutlinedCardButton(
    modifier: Modifier = Modifier,
    title: @Composable RowScope.() -> Unit,
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    onClick: () -> Unit,
    colors: CardColors = CardDefaults.outlinedCardColors(),
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick,
        colors = colors
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { it() }
            title()
            trailingIcon?.let { it() }
        }
    }
}
