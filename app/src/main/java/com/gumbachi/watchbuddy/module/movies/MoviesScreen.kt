package com.gumbachi.watchbuddy.module.movies

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.ui.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.MediaTabRow
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.dialog.FilterDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaEditDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaSortDialog
import com.gumbachi.watchbuddy.ui.snackbars.showUndoSnackbar
import com.gumbachi.watchbuddy.ui.toolbars.MediaAppBar
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MoviesScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = koinViewModel(),
    focusedItemId: WatchBuddyID? = null,
    navigateToDetails: (Movie) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()
    val cardSettings = state.settings.card
    val movieSettings = state.settings.movies

    // Composables
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        focusedItemId?.let {
            runCatching {
                val movie = viewModel.findMovie(it)
                viewModel.changeWatchStatusTab(state.shownTabs.indexOf(movie?.watchStatus))
                val index = state.currentList.indexOf(movie)
                gridState.animateScrollToItem(index)
            }.onFailure {
                Log.d(TAG, "Couldn't navigate to specific Movie: $it")
            }
        }
    }

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier,
        topBar = {
            MediaAppBar(
                title = "Movies",
                onSortClicked = viewModel::showSortDialog,
                onFilterClicked = viewModel::showFilterDialog
            )
        },
        snackbarHost = {
            SnackbarHost(snackbar)
        },
        tabRow = {
            MediaTabRow(
                selected = state.selectedTab,
                tabs = state.shownTabs,
                onSelectedChange = { index, _ ->
                    viewModel.changeWatchStatusTab(index)
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(cardSettings.style.requiredWidth),
            horizontalArrangement = Arrangement.Center,
            state = gridState,
            contentPadding = PaddingValues(4.dp),
        ) {
            items(
                items = state.currentList,
                key = { it.watchbuddyID.toString() }
            ) { movie ->

                val showScore = when (cardSettings.showScoreOnPlanned) {
                    true -> true
                    false -> movie.watchStatus != WatchStatus.Planning
                }

                MediaCard(
                    cardData = movie,
                    cardStyle = cardSettings.style,
                    scoreFormat = cardSettings.scoreFormat,
                    showApi = cardSettings.showApi,
                    onClick = { navigateToDetails(movie) },
                    onLongClick = { viewModel.showEditDialogFor(movie) },
                    modifier = Modifier.animateItemPlacement(),
                    showScore = showScore
                )
            }
        }
    }

    MediaSortDialog(
        showDialog = state.showSortDialog,
        title = "Sort Movies",
        defaultSort = movieSettings.sort,
        defaultOrder = movieSettings.sortOrder,
        onDismissRequest = viewModel::hideSortDialog,
        onConfirm = viewModel::updateMovieSort,
    )

    MediaEditDialog(
        title = "Edit Movie",
        isMediaSaved = true,
        media = state.movieUnderEdit,
        scoreFormat = cardSettings.scoreFormat,
        hiddenStatuses = movieSettings.hiddenStatuses,
        showProgressSection = false,
        onCancel = viewModel::hideEditDialog,
        onDismissRequest = {},
        onMediaDelete = {
            state.movieUnderEdit?.let { original ->
                viewModel.deleteMovie(original)
                snackbar.showUndoSnackbar(
                    scope = scope,
                    message = "Movie Deleted",
                    undo = { viewModel.undoDelete(original) }
                )
            }
        },
        onConfirm = { updated ->
            state.movieUnderEdit?.let { original ->
                viewModel.updateMovie(updated as Movie)
                snackbar.showUndoSnackbar(
                    scope = scope,
                    message = "Movie Updated",
                    undo = { viewModel.updateMovie(original) }
                )
            }
        }
    )

    FilterDialog(
        showDialog = state.showFilterDialog,
        title = "Filter Movies",
        initialFilter = state.filter,
        onConfirm = viewModel::updateFilter,
        onDismissRequest = viewModel::hideFilterDialog,
        showMediaTypeFilters = false
    )

}
