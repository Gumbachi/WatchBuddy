package com.gumbachi.watchbuddy.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun MediaSortDialog(
    title: String,
    modifier: Modifier = Modifier,
    defaultSort: Sort = Sort.ScoreDescending,
    onSortChange: (Sort) -> Unit = {},
) {
    WatchBuddyDialog(
        title = title,
        icon = rememberVectorPainter(image = Icons.Filled.Sort),
        modifier = modifier,
        onDismissRequest = { onSortChange(defaultSort) }
    ) {
        Sort.values().forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onSortChange(it) }
            ) {
                RadioButton(
                    selected = it == defaultSort,
                    onClick = { onSortChange(it) },
                )
                Text(text = it.label, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview
@Composable
private fun PreviewSortDialog(
    darkMode: Boolean = true
) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            MediaSortDialog(
                title = "Sort Movies"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSortDialogLight() {
    PreviewSortDialog(false)
}