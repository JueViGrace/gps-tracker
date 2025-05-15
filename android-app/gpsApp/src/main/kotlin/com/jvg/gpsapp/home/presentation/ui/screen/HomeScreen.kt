package com.jvg.gpsapp.home.presentation.ui.screen

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.jvg.gpsapp.home.presentation.state.HomeState
import com.jvg.gpsapp.home.presentation.ui.components.CurrentLocationComponent
import com.jvg.gpsapp.home.presentation.ui.components.TrackingList
import com.jvg.gpsapp.home.presentation.viewmodel.HomeViewModel
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.Fonts
import com.jvg.gpsapp.ui.components.LocationEffect
import com.jvg.gpsapp.ui.components.standard.display.TextComponent
import com.jvg.gpsapp.ui.components.standard.layout.bars.TopBarComponent
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
)
@Composable
fun HomeScreen() {
    val viewmodel: HomeViewModel = koinViewModel()
    val state: HomeState by viewmodel.state.collectAsStateWithLifecycle()

    var locationRequest by remember {
        mutableStateOf<LocationRequest?>(null)
    }
    val coarsePermission = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    val finePermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(true) {
        coarsePermission.launchPermissionRequest()
    }

    LaunchedEffect(true) {
        finePermission.launchPermissionRequest()
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = {
                    TextComponent(
                        text = stringResource(R.string.locations),
                        style = Fonts.extraLargeTextStyle,
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(visible = coarsePermission.status.isGranted && finePermission.status.isGranted) {
                LaunchedEffect(true) {
                    val usePreciseLocation = if (finePermission.status.isGranted) {
                        Priority.PRIORITY_HIGH_ACCURACY
                    } else {
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY
                    }
                    locationRequest = LocationRequest.Builder(
                        usePreciseLocation,
                        TimeUnit.SECONDS.toMillis(3)
                    ).build()
                }

                if (locationRequest != null) {
                    LocationEffect(
                        locationRequest = locationRequest!!,
                    ) { location ->
                        viewmodel.updateLocation(location.lastLocation)
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrentLocationComponent(
                        tracking = state.tracking
                    )
                    HorizontalDivider()
                    TrackingList(state)
                }
            }

            AnimatedVisibility(
                visible = !coarsePermission.status.isGranted || !finePermission.status.isGranted
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextComponent(
                        text = stringResource(R.string.permission_required),
                        textAlign = TextAlign.Center,
                        style = Fonts.extraLargeTextStyle,
                    )
                    TextComponent(
                        text = stringResource(R.string.location_required),
                        textAlign = TextAlign.Center,
                        style = Fonts.mediumTextStyle,
                    )
                    TextComponent(
                        text = stringResource(R.string.please_grant_permission),
                        textAlign = TextAlign.Center,
                    )

                    if (!coarsePermission.status.isGranted) {
                        FilledTonalButton(
                            onClick = {
                                coarsePermission.launchPermissionRequest()
                            }
                        ) {
                            TextComponent(
                                text = stringResource(R.string.grant_coarse_permission)
                            )
                        }
                    }

                    if (!finePermission.status.isGranted) {
                        FilledTonalButton(
                            onClick = {
                                finePermission.launchPermissionRequest()
                            }
                        ) {
                            TextComponent(
                                text = stringResource(R.string.grant_fine_permission)
                            )
                        }
                    }
                }
            }
        }
    }
}
