package com.gumbachi.watchbuddy.ui.screens.movies

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.model.enums.WatchStatus
import com.gumbachi.watchbuddy.ui.app.components.CompactMediaCard
import com.gumbachi.watchbuddy.ui.app.components.MediaCard
import com.gumbachi.watchbuddy.ui.app.components.WatchbuddyTabRow
import com.gumbachi.watchbuddy.ui.screens.movies.components.EditDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesScreenViewModel = viewModel(),
    navigateToMovieDetails: (SourceAPI, Int) -> Unit = { _, _ -> } // TODO Update this to Movie
) {

    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.updateSettings()
        Log.d("Movies", "Launched Effect: Updating Settings to ${state.cardStyle}")
    }

    Column(modifier = modifier) {
        WatchbuddyTabRow(
            tabTitles = WatchStatus.values().map { it.toString() },
            selectedTab = state.selectedTab,
            onTabChange = { viewModel.onTabChange(it) }
        )

        if (state.loading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(state.cardStyle.columns),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(2.dp)
        ) {
            state.movies?.let { movies ->
                items(movies) { movie ->
                    when (state.cardStyle) {
                        CardStyle.Normal -> {
                            MediaCard(
                                movie = movie,
                                onClick = { navigateToMovieDetails(movie.api, movie.id) },
                                onLongClick = { viewModel.displayEditDialog(movie) },
                                scoreFormat = state.scoreFormat
                            )
                        }
                        CardStyle.Compact -> {
                            CompactMediaCard(
                                movie = movie,
                                onClick = { navigateToMovieDetails(movie.api, movie.id) },
                                onLongClick = { viewModel.displayEditDialog(movie) },
                                scoreFormat = state.scoreFormat
                            )
                        }
                    }
                }
            }
        }
    }
    if (state.showEditMenu) {
        state.editMovie?.let {
            EditDialog(
                title = "Edit: ${it.title}",
                defaultStatus = it.watchStatus,
                defaultScore = it.userScore,
                onCancelClicked = { viewModel.hideEditDialog() }
            )
        }
    }
}