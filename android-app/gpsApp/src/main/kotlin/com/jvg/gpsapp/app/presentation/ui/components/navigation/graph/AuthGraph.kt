package com.jvg.gpsapp.app.presentation.ui.components.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jvg.gpsapp.auth.presentation.ui.screen.AuthScreen
import com.jvg.gpsapp.ui.components.standard.navigation.enterLeftOutRightComposable
import com.jvg.gpsapp.ui.navigation.Destination

fun NavGraphBuilder.authGraph() {
    navigation<Destination.AuthGraph>(
        startDestination = Destination.Auth,
    ) {
        enterLeftOutRightComposable<Destination.Auth> { backStackEntry ->
            AuthScreen()
        }
    }
}
