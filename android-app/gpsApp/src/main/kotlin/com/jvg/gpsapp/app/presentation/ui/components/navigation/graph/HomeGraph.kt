package com.jvg.gpsapp.app.presentation.ui.components.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jvg.gpsapp.ui.components.standard.navigation.enterRightOutLeftComposable
import com.jvg.gpsapp.ui.navigation.Destination
import com.jvg.gpsapp.home.presentation.screen.HomeScreen

fun NavGraphBuilder.homeGraph() {
    navigation<Destination.HomeGraph>(
        startDestination = Destination.Home,
    ) {
        enterRightOutLeftComposable<Destination.Home> { backStackEntry ->
            HomeScreen()
        }
    }
}
