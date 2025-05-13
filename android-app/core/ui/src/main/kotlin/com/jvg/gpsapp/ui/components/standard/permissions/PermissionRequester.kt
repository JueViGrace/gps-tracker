package com.jvg.gpsapp.ui.components.standard.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequester(
    permission: String,
    isGranted: (Boolean) -> Unit,
) {
    val launcher = rememberPermissionState(
        permission = permission,
        onPermissionResult = isGranted
    )

    LaunchedEffect(true) {

    }
}
