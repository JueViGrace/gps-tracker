package com.jvg.gpsapp.home.presentation.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.app.presentation.ui.components.permissions.PermissionRequester
import com.jvg.gpsapp.home.presentation.viewmodel.HomeViewModel
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.Fonts
import com.jvg.gpsapp.ui.components.LocationComponent
import com.jvg.gpsapp.ui.components.standard.display.TextComponent
import com.jvg.gpsapp.ui.components.standard.layout.bars.TopBarComponent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewmodel: HomeViewModel = koinViewModel()

    PermissionRequester(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
    ) { isGranted ->
    }

    PermissionRequester(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
    ) { isGranted ->
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
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.your_location),
                    textAlign = TextAlign.Center,
                    style = Fonts.mediumTextStyle,
                )

                LocationComponent(
                    title = stringResource(R.string.latitude),
                    value = "",
                )
                LocationComponent(
                    title = stringResource(R.string.longitude),
                    value = "",
                )
                LocationComponent(
                    title = stringResource(R.string.altitude),
                    value = "",
                )
                LocationComponent(
                    title = stringResource(R.string.time),
                    value = "",
                )
            }
        }
    }
}
