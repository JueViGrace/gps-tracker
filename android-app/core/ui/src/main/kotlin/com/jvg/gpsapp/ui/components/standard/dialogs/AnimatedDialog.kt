package es.red.tcd.ui.components.standard.dialogs

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedDialog(
    modifier: Modifier = Modifier,
    @RawRes
    icon: Int,
    @StringRes
    titleText: Int,
    description: (@Composable () -> Unit)? = null,
    customContent: (@Composable ColumnScope.() -> Unit)? = null,
    hasConfirmButton: Boolean = true,
    hasDismissButton: Boolean = false,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(icon))
    val progress by animateLottieCompositionAsState(composition)

    DefaultDialog(
        modifier = modifier,
        icon = {
            LottieAnimation(
                modifier = Modifier.size(130.dp),
                composition = composition,
                progress = { progress },
            )
        },
        hasConfirmButton = hasConfirmButton,
        hasDismissButton = hasDismissButton,
        titleText = titleText,
        description = description,
        customContent = customContent,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}
