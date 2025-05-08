package com.jvg.gpsapp.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvg.gpsapp.auth.presentation.state.AuthState
import com.jvg.gpsapp.auth.presentation.viewmodel.AuthViewModel
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.components.standard.display.TextComponent
import com.jvg.gpsapp.ui.components.standard.icons.BackgroundIcon
import com.jvg.gpsapp.ui.components.standard.layout.loading.CircularLoadingComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    viewmodel: AuthViewModel = koinViewModel()
) {
    val state: AuthState by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewmodel.initSession()
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!state.isAuthenticated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (state.isLoading) {
                        CircularLoadingComponent()
                    } else {
                        BackgroundIcon(
                            contentPadding = PaddingValues(4.dp),
                            backgroundColor = MaterialTheme.colorScheme.errorContainer,
                            painter = painterResource(R.drawable.ic_x),
                            contentDescription = stringResource(R.string.error),
                        )
                    }
                    state.message?.let { TextComponent(text = stringResource(it)) }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackgroundIcon(
                        contentPadding = PaddingValues(4.dp),
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = stringResource(R.string.success),
                    )
                    state.message?.let { TextComponent(text = stringResource(it)) }
                }
            }
        }
    }
}
