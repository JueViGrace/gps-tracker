package com.jvg.gpsapp.home.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.types.tracking.Tracking
import com.jvg.gpsapp.ui.Fonts
import com.jvg.gpsapp.ui.components.LocationComponent
import com.jvg.gpsapp.ui.components.standard.display.TextComponent

@Composable
fun CurrentLocationComponent(
    tracking: Tracking?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = stringResource(R.string.your_current_location),
            style = Fonts.largeTextStyle,
        )

        LocationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 44.dp),
            title = "${stringResource(R.string.latitude)}:",
            value = tracking?.latitude?.toString()
                ?: stringResource(R.string.not_available),
        )
        LocationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 44.dp),
            title = "${stringResource(R.string.longitude)}:",
            value = tracking?.longitude?.toString()
                ?: stringResource(R.string.not_available),
        )
        LocationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 44.dp),
            title = "${stringResource(R.string.altitude)}:",
            value = tracking?.altitude?.toString()
                ?: stringResource(R.string.not_available),
        )
        LocationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 44.dp),
            title = "${stringResource(R.string.time)}:",
            value = tracking?.time?.toString()
                ?: stringResource(R.string.not_available),
        )
    }
}
