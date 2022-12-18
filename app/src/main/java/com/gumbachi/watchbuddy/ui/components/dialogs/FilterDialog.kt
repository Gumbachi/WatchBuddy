package com.gumbachi.watchbuddy.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.OldMediaFilter
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OldMediaFilterDialog(
    title: String,
    filter: MediaFilter,
    isSaveAsDefaultChecked: Boolean,
    onSubmit: (filter: MediaFilter, saveAsDefault: Boolean) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var defaultChecked by remember { mutableStateOf(isSaveAsDefaultChecked) }
    var state by remember { mutableStateOf(filter) }

    WatchBuddyDialog(
        title = title,
        modifier = modifier,
        icon = rememberVectorPainter(image = Icons.Filled.FilterList),
        actions = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel")
            }
            TextButton(onClick = { onSubmit(state, defaultChecked) }) {
                Text(text = "Done")
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "API",
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
                val selected = state.includes(api)
                FilterChip(
                    selected = selected,
                    onClick = {
                        state = if (selected) {
                            state.copy(allowedAPIs = state.allowedAPIs - api)
                        } else {
                            state.copy(allowedAPIs = state.allowedAPIs + api)
                        }
                    },
                    leadingIcon = {
                        if (selected) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                        }
                    },
                    label = { Text(text = api.name) },
                )
            }
        }



        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Media Format",
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
            MediaType.values().forEach { type ->
                val selected = state.includes(mediaType = type)
                FilterChip(
                    selected = selected,
                    onClick = {
                        state = if (selected) {
                            state.copy(allowedMediaTypes = state.allowedMediaTypes - type)
                        } else {
                            state.copy(allowedMediaTypes = state.allowedMediaTypes + type)
                        }
                    },
                    leadingIcon = {
                        if (selected) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                        }
                    },
                    label = { Text(text = type.name) },
                )
            }
        }

        Divider()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = defaultChecked,
                onCheckedChange = { defaultChecked = it }
            )
            Text(text = "Save as Default")
        }
    }
}


@Composable
fun SearchScreenFilterDialog(
    modifier: Modifier = Modifier,
    title: String = "Filter",
    defaultFilter: OldMediaFilter = OldMediaFilter(),
    onSubmit: (OldMediaFilter) -> Unit = {}

) {

    var filter by remember { mutableStateOf(defaultFilter) }

    WatchBuddyDialog(
        title = title,
        modifier = modifier,
        icon = rememberVectorPainter(image = Icons.Filled.FilterList),
        actions = {
            TextButton(onClick = { onSubmit(filter) }) {
                Text(text = "Done")
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "API",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(end = 4.dp)
            )
            Divider()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = filter.includeTMDB,
                onCheckedChange = { filter = filter.copy(includeTMDB = it) }
            )
            Text(text = "TMDB")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = filter.includeAnilist,
                onCheckedChange = { filter = filter.copy(includeAnilist = it) }
            )
            Text(text = "AniList")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Type",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(end = 4.dp)
            )
            Divider()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = filter.includeMovies,
                onCheckedChange = { filter = filter.copy(includeMovies = it) }
            )
            Text(text = "Movies")
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = filter.includeShows,
                onCheckedChange = { filter = filter.copy(includeShows = it) }
            )
            Text(text = "Shows")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFilterDialog(darkMode: Boolean = true) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            OldMediaFilterDialog(
                title = "Filter Dialog",
                filter = MediaFilter(),
                isSaveAsDefaultChecked = false,
                onSubmit = { _, _ -> },
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