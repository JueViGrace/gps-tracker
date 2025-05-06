package es.red.tcd.ui.components.standard.display

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.red.tcd.resources.R
import com.jvg.gpsapp.ui.Fonts
import io.github.joelkanyi.sain.Sain
import io.github.joelkanyi.sain.SignatureAction
import io.github.joelkanyi.sain.rememberSignatureState

@Composable
fun SignatureComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onChanged: (Boolean) -> Unit = {},
    onComplete: (ImageBitmap?) -> Unit,
    onClear: () -> Unit,
) {
    val state = rememberSignatureState()
    LaunchedEffect(state.signatureLines) {
        onChanged(state.signatureLines.isNotEmpty())
    }

    Sain(
        modifier = modifier,
        state = state,
        signatureHeight = 250.dp,
        signatureColor = MaterialTheme.colorScheme.onSurface,
        signaturePadColor = MaterialTheme.colorScheme.surfaceContainer,
        signatureBorderStroke = BorderStroke(
            width = .5.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        signaturePadShape = RoundedCornerShape(8.dp),
        onComplete = onComplete
    ) { action ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    action(SignatureAction.CLEAR)
                    onClear()
                },
                enabled = state.signatureLines.isNotEmpty()
            ) {
                TextComponent(
                    text = stringResource(id = R.string.erase),
                    style = Fonts.mediumTextStyle
                )
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    action(SignatureAction.COMPLETE)
                },
                enabled = enabled
            ) {
                TextComponent(
                    text = stringResource(id = R.string.sign),
                    style = Fonts.mediumTextStyle
                )
            }
        }
    }
}
