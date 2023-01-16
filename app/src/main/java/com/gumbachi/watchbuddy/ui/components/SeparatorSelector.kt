package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SeparatorSelector(
    topLabel: String,
    topItems: Collection<T>,
    bottomLabel: String,
    bottomItems: Collection<T>,
    onChange: (top: Collection<T>, bottom: Collection<T>) -> Unit,
    modifier: Modifier = Modifier,
    topIcon: ImageVector? = null,
    bottomIcon: ImageVector? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            topIcon?.let {
                Icon(imageVector = it, contentDescription = null)
            }
            Text(text = topLabel, style = MaterialTheme.typography.labelLarge)
        }
        FlowRow(
            mainAxisSpacing = 8.dp,
            lastLineMainAxisAlignment = MainAxisAlignment.Center,
            mainAxisSize = SizeMode.Wrap
        ) {
            topItems.forEach { item ->
                SuggestionChip(
                    onClick = { onChange(topItems - item, bottomItems + item) },
                    label = { Text(text = item.toString()) },
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1F, fill = false))
            Icon(
                imageVector = Icons.Filled.SwapVert,
                contentDescription = null,
                modifier = Modifier
            )
            Divider(modifier = Modifier.weight(1F, fill = false))
        }

        FlowRow(
            mainAxisSpacing = 8.dp,
            lastLineMainAxisAlignment = MainAxisAlignment.Center,
            mainAxisSize = SizeMode.Wrap
        ) {
            bottomItems.forEach { item ->
                SuggestionChip(
                    onClick = { onChange(topItems + item, bottomItems - item) },
                    label = { Text(text = item.toString()) }
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            bottomIcon?.let {
                Icon(imageVector = it, contentDescription = null)
            }
            Text(text = bottomLabel, style = MaterialTheme.typography.labelLarge)
        }

    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            SeparatorSelector(
                topLabel = "Shown",
                topItems = listOf(
                    WatchStatus.Planning,
                    WatchStatus.Completed,
                    WatchStatus.Dropped,
                ),
                bottomLabel = "Hidden",
                bottomItems = listOf(WatchStatus.Watching, WatchStatus.Repeating),
                onChange = { _, _ -> },
                topIcon = Icons.Filled.Visibility,
                bottomIcon = Icons.Filled.VisibilityOff
            )
        }
    }
}