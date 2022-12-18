package com.gumbachi.watchbuddy.ui.components.dialogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterDialog(
    title: String,
    filter: MediaFilter,
    onFilterChange: (MediaFilter) -> Unit,
    onFinish: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WatchBuddyDialog(
        title = title,
        modifier = modifier,
        icon = rememberVectorPainter(image = Icons.Filled.FilterList),
        actions = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel")
            }
            TextButton(onClick = onFinish) {
                Text(text = "Done")
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Source",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(end = 4.dp)
            )
            Divider()
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 4.dp,
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            API.values().forEach { api ->
                val selected = filter.includes(api)
                FilterChip(
                    selected = selected,
                    onClick = {
                        when (selected) {
                            true -> onFilterChange(filter - api)
                            false -> onFilterChange(filter + api)
                        }
                    },
                    leadingIcon = {
                        if (selected) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                        } else {
                            Icon(
                                painter = painterResource(id = api.logo),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    label = { Text(text = api.name) },
                )
            }
        }
    }
}

//region Previews
@Preview(showBackground = true)
@Composable
private fun PreviewFilterDialog(darkMode: Boolean = true) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            MediaFilterDialog(
                title = "Filter Media",
                filter = MediaFilter(),
                onFilterChange = {},
                onFinish = {},
                onCancel = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFilterDialogLight() {
    PreviewFilterDialog(darkMode = false)
}
//endregion