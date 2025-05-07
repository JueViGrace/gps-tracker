package com.jvg.gpsapp.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jvg.gpsapp.auth.presentation.viewmodel.AuthViewModel
import com.jvg.gpsapp.ui.components.standard.display.TextComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    viewmodel: AuthViewModel = koinViewModel()
) {
    LaunchedEffect(true) {
        viewmodel.initSession()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(text = "Loading session")
            CircularProgressIndicator()
        }
    }
}
