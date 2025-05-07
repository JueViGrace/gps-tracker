package com.jvg.gpsapp.app.presentation.ui.screens

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.jvg.gpsapp.R
import com.jvg.gpsapp.shared.presentation.ui.screens.splash.SplashScreen
import com.jvg.gpsapp.ui.components.observable.ObserveAsEvents
import com.jvg.gpsapp.ui.messages.Messages
import com.jvg.gpsapp.ui.navigation.Destination
import com.jvg.gpsapp.ui.navigation.NavigationAction
import com.jvg.gpsapp.ui.navigation.Navigator
import com.jvg.gpsapp.app.presentation.ui.components.navigation.graph.homeGraph
import com.jvg.gpsapp.app.presentation.viewmodel.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val messages: Messages = koinInject()
    val navigator: Navigator = koinInject()
    val navController: NavHostController = rememberNavController()

    val scope: CoroutineScope = rememberCoroutineScope()
    val hostState: SnackbarHostState = remember { SnackbarHostState() }
    val context: Context = LocalContext.current

    ObserveAsEvents(
        flow = messages.messages,
    ) { msg ->
        scope.launch {
            hostState.showSnackbar(
                "${msg.message.let { context.getString(it) }}, ${msg.description ?: ""}"
            )
        }
    }

    ObserveAsEvents(
        flow = navigator.navigationActions,
    ) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                if (action.destination is Destination.Auth) {
                    viewModel.invalidSession()
                }
                navigator.consumeAction(action)
                navController.navigate(action.destination, navOptions = action.navOptions)
            }
            NavigationAction.NavigateUp -> {
                navigator.consumeAction(action)
                navController.navigateUp()
            }

            is NavigationAction.PopUntil -> {
                navController.popBackStack(
                    route = action.destination,
                    inclusive = action.inclusive,
                    saveState = action.saveState,
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        NavHost(
            navController = navController,
            startDestination = navigator.startDestination
        ) {
            navigation<Destination.Root>(
                startDestination = Destination.Splash
            ) {
                composable<Destination.Splash>(
                    enterTransition = {
                        fadeIn(tween(500))
                    },
                    exitTransition = {
                        fadeOut(tween(500))
                    },
                    popEnterTransition = {
                        fadeIn(tween(500))
                    },
                    popExitTransition = {
                        fadeOut(tween(500))
                    }
                ) { _ ->
                    SplashScreen(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        onRender = {
                            scope.launch {
                                // todo: connect to the server and navigate home
                            }
                        }
                    )
                }
                homeGraph()
            }
        }

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = hostState,
            snackbar = { snackBarData ->
                Snackbar(
                    modifier = Modifier.zIndex(1f),
                    snackbarData = snackBarData,
                )
            }
        )
    }
}
