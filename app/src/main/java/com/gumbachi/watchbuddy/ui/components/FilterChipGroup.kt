package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterChipGroup(
    options: Collection<T>,
    selected: (option: T) -> Boolean,
    onOptionClick: (option: T, selected: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (option: T) -> Painter? = { null },
) {
    FlowRow(
        mainAxisSpacing = 8.dp,
        mainAxisAlignment = MainAxisAlignment.Center,
        modifier = modifier
    ) {
        options.forEach { option ->
            FilterChip(
                selected = selected(option),
                onClick = { onOptionClick(option, selected(option)) },
                leadingIcon = {
                    icon(option)?.let {
                        Icon(painter = it, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                },
                label = { Text(text = option.toString()) },
            )
        }
    }
}