package com.jvg.gpsapp.ui.components.standard.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jvg.gpsapp.resources.R
import com.jvg.gpsapp.ui.Fonts
import com.jvg.gpsapp.ui.components.standard.display.TextComponent

@Composable
fun DefaultDialog(
    modifier: Modifier = Modifier,
    hasConfirmButton: Boolean = false,
    @StringRes
    confirmText: Int = R.string.ok,
    hasDismissButton: Boolean = false,
    @StringRes
    cancelText: Int = R.string.cancel,
    icon: @Composable (ColumnScope.() -> Unit)? = null,
    @StringRes
    titleText: Int,
    description: @Composable (() -> Unit)? = null,
    customContent: (@Composable ColumnScope.() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    dismissButtonColor: ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colorScheme.error,
    ),
    confirmButtonColor: ButtonColors = ButtonDefaults.elevatedButtonColors(
        contentColor = MaterialTheme.colorScheme.primary,
    ),
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .sizeIn(
                    minWidth = 280.dp,
                    maxWidth = 580.dp
                )
                .padding(4.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = AlertDialogDefaults.shape,
                )
                .clip(AlertDialogDefaults.shape),
            propagateMinConstraints = true
        ) {
            Column(
                modifier = Modifier.padding(8.dp).then(modifier),
                verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                icon?.let { it() }
                Column(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextComponent(
                        text = stringResource(id = titleText),
                        style = Fonts.largeTextStyle,
                        color = titleColor,
                        textAlign = TextAlign.Center
                    )
                    description?.invoke()
                }
                customContent?.let { it() }
                if (hasConfirmButton) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (hasDismissButton) {
                            Alignment.CenterEnd
                        } else {
                            Alignment.Center
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.6f),
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 8.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (hasDismissButton) {
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = onDismiss,
                                    colors = dismissButtonColor
                                ) {
                                    TextComponent(
                                        text = stringResource(id = cancelText)
                                    )
                                }
                            }
                            onConfirm?.let { confirm ->
                                ElevatedButton(
                                    modifier = if (hasDismissButton) {
                                        Modifier.weight(1f)
                                    } else {
                                        Modifier
                                    },
                                    onClick = confirm,
                                    colors = confirmButtonColor
                                ) {
                                    TextComponent(
                                        text = stringResource(id = confirmText)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
