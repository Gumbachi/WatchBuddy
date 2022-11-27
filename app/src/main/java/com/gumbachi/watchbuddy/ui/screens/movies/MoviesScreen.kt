package com.gumbachi.watchbuddy.ui.screens.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gumbachi.watchbuddy.components.WatchStatusTabRow
import com.gumbachi.watchbuddy.components.appbars.WatchbuddyMediaAppBar
import com.gumbachi.watchbuddy.components.cards.CompactMediaCard
import com.gumbachi.watchbuddy.components.cards.MediaCard
import com.gumbachi.watchbuddy.components.dialogs.MediaEditDialog
import com.gumbachi.watchbuddy.components.dialogs.MediaSortDialog
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.interfaces.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "MoviesScreen"

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesScreenViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit = {},
    navigateToDetails: (Movie) -> Unit = {}
) {

    // State shortcuts
    val state = viewModel.state
    val userSettings = state.settings

    // Composables
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()

    Scaffold(
        modifier = modifier,
        topBar = {
            WatchbuddyMediaAppBar(
                title = "Movies",
                onSearchClicked = navigateToSearch,
                onFilterClicked = viewModel::showSortDialog
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            if (state.loading) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                return@Column
            }

            WatchStatusTabRow(
                selected = state.currentTab,
                onSelectedChange = { viewModel.onTabChange(it) },
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(userSettings.cardStyle.requiredWidth),
                horizontalArrangement = Arrangement.Center,
                state = gridState,
                contentPadding = PaddingValues(4.dp)
            ) {
                items(state.currentList, key = { it.id }) { movie ->
                    when (userSettings.cardStyle) {
                        CardStyle.Normal -> {
                            MediaCard(
                                movie = movie,
                                onClick = { navigateToDetails(movie) },
                                onLongClick = { viewModel.showEditDialogFor(movie) },
                                scoreFormat = userSettings.scoreFormat,
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                        CardStyle.Compact -> {
                            CompactMediaCard(
                                movie = movie,
                                onClick = { navigateToDetails(movie) },
                                onLongClick = { viewModel.showEditDialogFor(movie) },
                                scoreFormat = userSettings.scoreFormat,
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialogs/Menus
    if (state.showSortDialog) {
        MediaSortDialog(
            title = "Sort Movies By",
            defaultSort = userSettings.movieSort,
            onSortChange = {
                scope.launch {
                    viewModel.onSortChange(it)
                    delay(400L)
                    gridState.animateScrollToItem(0)
                }
            },
        )
    }

    state.movieUnderEdit?.let { movie ->
        MediaEditDialog(
            title = movie.title,
            editable = movie,
            scoreFormat = userSettings.scoreFormat,
            onCancel = { viewModel.hideEditDialog() },
            onDelete = { viewModel.onMovieDelete(snackbarState = snackbarHostState) },
            onSubmit = { viewModel.onMovieUpdate(edits = it, snackbarState = snackbarHostState) }
        )
    }
}
