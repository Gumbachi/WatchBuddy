package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun SpacedSection(
    label: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageVector? = null,
    action: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1F).padding(end = 8.dp)
        ) {
            icon?.let {
                Icon(imageVector = it, contentDescription = label)
            }
            Column {
                Text(text = label, style = MaterialTheme.typography.titleMedium)
                description?.let {
                    Text(text = it, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                }
            }
        }
        action()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            SpacedSection(label = "Label", icon = Icons.Default.Check, description = "Howdy") {
                Switch(checked = true, onCheckedChange = {})
            }
        }
    }
}