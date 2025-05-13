package com.jvg.gpsapp.home.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.home.presentation.state.HomeState
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.Fonts
import com.jvg.gpsapp.ui.components.LocationComponent
import com.jvg.gpsapp.ui.components.standard.display.TextComponent
import com.jvg.gpsapp.ui.components.standard.layout.loading.CircularLoadingComponent
import com.jvg.gpsapp.util.Dates.toReadableDate

@Composable
fun TrackingList(state: HomeState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = stringResource(R.string.previous_locations),
            style = Fonts.largeTextStyle,
        )

        when {
            state.trackingLoading -> {
                CircularLoadingComponent()
            }

            state.trackingError -> {
                TextComponent(
                    text = stringResource(R.string.unexpected_error),
                    style = Fonts.mediumTextStyle,
                )
            }

            else -> {
                LazyColumn {
                    if (state.trackingList.isEmpty()) {
                        item {
                            TextComponent(
                                text = stringResource(R.string.no_items),
                                style = Fonts.mediumTextStyle,
                            )
                        }
                    } else {
                        items(
                            items = state.trackingList,
                            key = { item -> "${item.longitude}-${item.latitude}-${item.time}" }
                        ) { item ->
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextComponent(
                                    text = "${stringResource(R.string.location_at)}: ${item.time.toReadableDate()}",
                                    style = Fonts.mediumTextStyle,
                                )

                                LocationComponent(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 44.dp),
                                    title = "${stringResource(R.string.latitude)}:",
                                    value = item.latitude.toString()
                                )
                                LocationComponent(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 44.dp),
                                    title = "${stringResource(R.string.longitude)}:",
                                    value = item.longitude.toString()
                                )
                                LocationComponent(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 44.dp),
                                    title = "${stringResource(R.string.altitude)}:",
                                    value = item.altitude.toString()
                                )
                                LocationComponent(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 44.dp),
                                    title = "${stringResource(R.string.time)}:",
                                    value = item.time.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
