package com.jvg.gpsapp.ui.components.standard.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.jvg.gpsapp.ui.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun BackHandlerComponent(navigator: Navigator, callBack: (() -> Unit)? = null) {
    val scope = rememberCoroutineScope()
    BackHandler {
        if (callBack != null) {
            callBack()
        } else {
            scope.launch(Dispatchers.Main.immediate) {
                awaitFrame()
                navigator.navigateUp()
            }
        }
    }
}
