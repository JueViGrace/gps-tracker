package com.jvg.gpsapp.ui.components.standard.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.Fonts
import es.red.tcd.ui.components.standard.display.CardContainerItem
import es.red.tcd.ui.components.standard.display.TextComponent
import es.red.tcd.ui.components.standard.icons.IconComponent

@Composable
fun DatePickerButton(
    modifier: Modifier = Modifier,
    showPicker: () -> Unit,
    date: String,
) {
    CardContainerItem(
        modifier = modifier,
        leadingIcon = {
            IconComponent(
                painter = painterResource(id = R.drawable.ic_calendar_week),
                contentDescription = stringResource(R.string.date)
            )
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponent(
                    text = stringResource(id = R.string.date),
                    style = Fonts.mediumTextStyle
                )

                ElevatedCard(
                    onClick = showPicker,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    TextComponent(
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        ),
                        text = date,
                    )
                }
            }
        },
    )
}
