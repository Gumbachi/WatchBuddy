package com.gumbachi.watchbuddy.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.SearchFilter
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun SearchScreenFilterDialog(
    modifier: Modifier = Modifier,
    title: String = "Search Filters",
    defaultFilter: SearchFilter = SearchFilter(),
    onSubmit: (SearchFilter) -> Unit = {}

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
                checked = filter.tmdb,
                onCheckedChange = { filter = filter.copy(tmdb = it) }
            )
            Text(text = "TMDB")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = filter.anilist,
                onCheckedChange = { filter = filter.copy(anilist = it) }
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
                checked = filter.movies,
                onCheckedChange = { filter = filter.copy(movies = it) }
            )
            Text(text = "Movies")
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = filter.shows,
                onCheckedChange = { filter = filter.copy(shows = it) }
            )
            Text(text = "Shows")
        }
    }
}

@Preview
@Composable
private fun PreviewFilterDialog(darkMode: Boolean = true) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface{
            SearchScreenFilterDialog()
        }
    }
}

@Preview
@Composable
private fun PreviewFilterDialogLight() {
    PreviewFilterDialog(darkMode = false)
}