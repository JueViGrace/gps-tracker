package es.red.tcd.ui.components.standard.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.red.tcd.core.types.TimeSelectorAction
import es.red.tcd.core.types.TimeSelectorType
import es.red.tcd.resources.R
import com.jvg.gpsapp.ui.Fonts
import es.red.tcd.ui.components.standard.display.TextComponent
import es.red.tcd.ui.components.standard.icons.IconComponent

@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    time: Int,
    type: TimeSelectorType = TimeSelectorType.HOURS,
    onTimeChanged: (Int) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextComponent(
            modifier = Modifier.weight(1f),
            text = "${
                stringResource(
                    id = when (type) {
                        TimeSelectorType.MINUTES -> R.string.minutes
                        TimeSelectorType.HOURS -> R.string.hours
                    }
                )
            }: $time",
            style = Fonts.mediumTextStyle
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconButton(
                onClick = {
                    onTimeChanged(calculateTime(time, TimeSelectorAction.SUBTRACT, type))
                }
            ) {
                IconComponent(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = stringResource(id = R.string.subtract),
                )
            }
            OutlinedIconButton(
                onClick = {
                    onTimeChanged(calculateTime(time, TimeSelectorAction.ADD, type))
                }
            ) {
                IconComponent(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = stringResource(id = R.string.add),
                )
            }
        }
    }
}

private fun calculateTime(time: Int, action: TimeSelectorAction, type: TimeSelectorType = TimeSelectorType.HOURS): Int {
    return when (type) {
        TimeSelectorType.MINUTES -> {
            when (action) {
                TimeSelectorAction.ADD -> {
                    if (time < 60) time + 5 else time
                }
                TimeSelectorAction.SUBTRACT -> {
                    if (time > 0) time - 5 else time
                }
            }
        }
        TimeSelectorType.HOURS -> {
            when (action) {
                TimeSelectorAction.ADD -> {
                    if (time < 24) time + 1 else time
                }
                TimeSelectorAction.SUBTRACT -> {
                    if (time > 0) time - 1 else time
                }
            }
        }
    }
}
