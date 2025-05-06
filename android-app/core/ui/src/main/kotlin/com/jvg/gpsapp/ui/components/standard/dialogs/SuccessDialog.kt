package es.red.tcd.ui.components.standard.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import es.red.tcd.resources.R

@Composable
fun SuccessDialog(
    modifier: Modifier = Modifier,
    description: (@Composable () -> Unit)? = null,
    hasConfirmButton: Boolean = true,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    AnimatedDialog(
        modifier = modifier,
        icon = R.raw.success,
        hasConfirmButton = hasConfirmButton,
        titleText = R.string.success,
        description = description,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}
