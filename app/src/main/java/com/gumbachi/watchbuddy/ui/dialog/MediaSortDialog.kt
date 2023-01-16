package com.gumbachi.watchbuddy.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.ui.components.LabeledDivider
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun MediaSortDialog(
    showDialog: Boolean,
    title: String,
    defaultSort: Sort,
    defaultOrder: SortOrder,
    onDismissRequest: () -> Unit,
    onConfirm: (sort: Sort, order: SortOrder) -> Unit,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = onDismissRequest,
) {

    if (!showDialog) return

    var sort by remember { mutableStateOf(defaultSort) }
    var order by remember { mutableStateOf(defaultOrder) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onConfirm(sort, order) }) {
                Text(text = "Confirm")
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel")
            }
        },
        icon = { Icon(Icons.Filled.Sort, null) },
        title = { Text(text = title) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                LabeledDivider(label = "BY")

                Sort.values().forEach {
                    MediaSortDialogRadioButtonOption(
                        label = it.label,
                        selected = it == sort,
                        onClick = { sort = it }
                    )
                }

                LabeledDivider(label = "ORDER")

                SortOrder.values().forEach {
                    MediaSortDialogRadioButtonOption(
                        label = it.toString(),
                        selected = order == it,
                        onClick = { order = it }
                    )
                }
            }
        }
    )
}

@Composable
private fun MediaSortDialogRadioButtonOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
        )
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun PreviewSortDialog(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            MediaSortDialog(
                showDialog = true,
                title = "Sort Movies",
                defaultSort = Sort.Score,
                defaultOrder = SortOrder.Descending,
                onDismissRequest = {},
                onConfirm = { _, _ -> },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewSortDialog(dark = false)
}