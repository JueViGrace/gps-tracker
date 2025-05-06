package es.red.tcd.ui.components.standard.input

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.jvg.gpsapp.ui.Fonts
import es.red.tcd.ui.components.standard.display.DigitContainer

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    supportingText: @Composable (() -> Unit)? = null,
    @IntRange(from = 4, to = 8) maxLength: Int = 6,
    isMasked: Boolean = false,
    mask: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = Fonts.largeTextStyle,
    enabled: Boolean = true,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= maxLength) {
                onValueChange(newValue)
            }
            if (newValue.length >= maxLength) {
                focusManager.clearFocus()
            }
        },
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = { _ ->
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(maxLength) { index ->
                        val char = when {
                            index >= value.length -> ""
                            else -> value[index].toString()
                        }
                        val isFocused = value.length == index
                        Spacer(modifier = Modifier.weight(0.1f))
                        DigitContainer(
                            isFocused = isFocused,
                            digit = char,
                            textStyle = textStyle,
                            isMasked = isMasked,
                            mask = mask,
                            isError = isError
                        )
                        Spacer(modifier = Modifier.weight(0.1f))
                    }
                }
                supportingText?.invoke()
            }
        }
    )
}
