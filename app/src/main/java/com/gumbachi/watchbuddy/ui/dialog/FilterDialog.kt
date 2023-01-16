package com.gumbachi.watchbuddy.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.ui.components.FilterChipGroup
import com.gumbachi.watchbuddy.ui.components.LabeledDivider
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun FilterDialog(
    showDialog: Boolean,
    title: String,
    initialFilter: MediaFilter,
    onConfirm: (MediaFilter) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = onDismissRequest,
    showAPIFilters: Boolean = true,
    showMediaTypeFilters: Boolean = true
) {

    if (!showDialog) return
    var state by remember { mutableStateOf(initialFilter) }

    AlertDialog(
        title = { Text(text = title) },
        modifier = modifier,
        icon = { Icon(imageVector = Icons.Filled.FilterList, contentDescription = null) },
        confirmButton = {
            TextButton(onClick = { onConfirm(state) }) {
                Text(text = "Done")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel")
            }
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (showAPIFilters) {
                    LabeledDivider(label = "SOURCE")
                    FilterChipGroup(
                        options = API.values().toList(),
                        selected = { state.includes(it) },
                        onOptionClick = { option, selected ->
                            when (selected) {
                                true -> state -= option
                                false -> state += option
                            }
                        },
                        icon = { painterResource(id = it.logo) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (showMediaTypeFilters) {
                    LabeledDivider(label = "TYPE")
                    FilterChipGroup(
                        options = MediaType.values().toList(),
                        selected = { state.includes(it) },
                        onOptionClick = { option, selected ->
                            when (selected) {
                                true -> state -= option
                                false -> state += option
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            FilterDialog(
                showDialog = true,
                title = "Filter Media",
                initialFilter = MediaFilter(),
                onConfirm = {},
                onDismissRequest = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    Preview(dark = false)
}