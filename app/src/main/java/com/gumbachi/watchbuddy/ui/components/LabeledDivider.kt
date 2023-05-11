package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun LabeledDivider(
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Divider()
    }
}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            LabeledDivider(label = "Label")
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(dark = false)
}