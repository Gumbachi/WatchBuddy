package com.gumbachi.watchbuddy.module.movies

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.ui.cards.LazyMediaGrid
import com.gumbachi.watchbuddy.ui.components.PagerMediaTabRow
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.dialog.FilterDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaEditDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaSortDialog
import com.gumbachi.watchbuddy.ui.snackbars.showUndoSnackbar
import com.gumbachi.watchbuddy.ui.toolbars.MediaAppBar
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MoviesScreen"

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class, ExperimentalPagerApi::class
)
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
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            println("Refresh")
            viewModel.refresh()
        }
    )
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.changeWatchStatusTab(page)
        }
    }

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
            PagerMediaTabRow(
                pagerState = pagerState,
                shownTabs = state.shownTabs,
                onSelectedChange = { index, _ ->
                    scope.launch { pagerState.scrollToPage(index) }
                }
            )
        }
    ) {
        Box(modifier = Modifier.pullRefresh(pullRefreshState, true)) {
            HorizontalPager(
                count = state.shownTabs.size,
                state = pagerState
            ) { page ->
                LazyMediaGrid(
                    items = state.watchlist.getListFor(state.shownTabs[page]),
                    settings = cardSettings,
                    onItemClick = { navigateToDetails(it) },
                    onItemLongClick = { viewModel.showEditDialogFor(it) }
                )
            }

            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3),
                contentColor = MaterialTheme.colorScheme.primary,
                scale = true
            )
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
