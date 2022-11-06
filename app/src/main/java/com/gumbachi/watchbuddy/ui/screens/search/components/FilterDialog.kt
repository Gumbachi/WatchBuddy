package com.gumbachi.watchbuddy.ui.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gumbachi.watchbuddy.R
import com.gumbachi.watchbuddy.ui.screens.search.FilterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenFilterDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    filterState: FilterState = FilterState(),
    onFilterStateChange: (FilterState) -> Unit = {}

) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "API",
                    style = MaterialTheme.typography.labelLarge
                )
                FilterChip(
                    selected = filterState.TMDB,
                    onClick = {
                        onFilterStateChange(filterState.copy(TMDB = !filterState.TMDB))
                    },
                    label = { Text(text = "TMDB") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.tmdb),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                )
                FilterChip(
                    selected = filterState.anilist,
                    onClick = { onFilterStateChange(filterState.copy(anilist = !filterState.anilist)) },
                    label = { Text(text = "AniList") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.anilist),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Type",
                    style = MaterialTheme.typography.labelLarge
                )
                FilterChip(
                    selected = filterState.movies,
                    onClick = { onFilterStateChange(filterState.copy(movies = !filterState.movies)) },
                    label = { Text(text = "Movies") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Movie,
                            contentDescription = null
                        )
                    }
                )
                FilterChip(
                    selected = filterState.shows,
                    onClick = { onFilterStateChange(filterState.copy(shows = !filterState.shows)) },
                    label = { Text(text = "Shows") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Tv,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}