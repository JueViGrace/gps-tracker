package es.red.tcd.ui.components.standard.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.red.tcd.core.shared.ui.model.FormItem
import com.jvg.gpsapp.ui.Fonts

@Composable
fun FormSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    items: List<FormItem>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceAround,
    colors: CardColors = CardDefaults.elevatedCardColors(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        title?.let { text ->
            TextComponent(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                text = text,
                style = Fonts.largeTextStyle
            )
        }

        ElevatedCardContainer(
            verticalArrangement = verticalArrangement,
            colors = colors
        ) {
            for ((index, item) in items.withIndex()) {
                CardContainerItem(
                    modifier = Modifier.fillMaxWidth().padding(contentPadding),
                    leadingIcon = item.leadingIcon,
                    title = item.title,
                    trailingIcon = item.trailingIcon,
                    onClick = item.onClick,
                )
                if (index < items.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun OutlinedFormSection(
    title: String? = null,
    items: List<FormItem>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceAround,
    colors: CardColors = CardDefaults.outlinedCardColors(),
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        title?.let { text ->
            TextComponent(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                text = text,
                style = Fonts.largeTextStyle
            )
        }

        OutlinedCardContainer(
            verticalArrangement = verticalArrangement,
            colors = colors
        ) {
            for ((index, item) in items.withIndex()) {
                CardContainerItem(
                    modifier = Modifier.fillMaxWidth().padding(contentPadding),
                    leadingIcon = item.leadingIcon,
                    title = item.title,
                    trailingIcon = item.trailingIcon,
                    onClick = item.onClick,
                )
                if (index < items.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

