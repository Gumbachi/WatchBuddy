package com.gumbachi.watchbuddy.module.movies

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.ui.components.MediaScreenScaffold
import com.gumbachi.watchbuddy.ui.components.MediaTabRow
import com.gumbachi.watchbuddy.ui.components.appbars.MediaAppBar
import com.gumbachi.watchbuddy.ui.components.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaEditDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaFilterDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaSortDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MoviesScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = koinViewModel(),
    focusedItemId: WatchbuddyID? = null,
    navigateToDetails: (Movie) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()

    // Composables
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()

//    LaunchedEffect(Unit) {
//        Log.d(TAG,"Launched Effect")
//        focusedItemId?.let {
//            viewModel.findItemOnScreen(id = it) { index, tab ->
//                viewModel.changeWatchStatusTab(tab)
//                delay(500)
//                gridState.animateScrollToItem(index)
//            }
//        }
//    }


    MediaScreenScaffold(
        isLoading = state.loading,
        error = state.error,
        topBar = {
            MediaAppBar(
                title = "Movies",
                onSortClicked = { viewModel.showSortDialog() },
                onFilterClicked = { viewModel.showFilterDialog() }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        tabRow = {
            MediaTabRow(
                selected = state.selectedTab,
                tabs = state.shownTabs,
                onSelectedChange = { index, _ ->
                    viewModel.changeWatchStatusTab(index)
                }
            )
        },
        dialogs = {
            // Sort Dialog
            if (state.showSortDialog) {
                MediaSortDialog(
                    title = "Sort Movies By",
                    defaultSort = state.settings.movieSort,
                    onSortChange = {
                        scope.launch {
                            viewModel.updateMovieSortTo(it)
                            delay(400L)
                            gridState.animateScrollToItem(0)
                        }
                    },
                )
            }

            // Filter Dialog
            if (state.showFilterDialog) {
                // temporary state
                var filter by remember { mutableStateOf(state.filter) }
                MediaFilterDialog(
                    title = "Filter Movies",
                    filter = filter,
                    onFilterChange = { filter = it },
                    onFinish = { viewModel.changeFilterTo(filter) },
                    onCancel = { viewModel.hideFilterDialog() }
                )
            }

            // Edit Dialog
            state.movieUnderEdit?.let { movie ->
                MediaEditDialog(
                    title = movie.title,
                    editable = movie.clone(),
                    scoreFormat = state.settings.scoreFormat,
                    onCancel = { viewModel.hideEditDialog() },
                    onDelete = { viewModel.deleteMovie(snackbarHostState) },
                    onSubmit = {
                        viewModel.updateMovie(
                            original = movie,
                            updated = it as Movie,
                            snackbarState = snackbarHostState
                        )
                    }
                )
            }
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(state.settings.cardStyle.requiredWidth),
            horizontalArrangement = Arrangement.Center,
            state = gridState,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(state.currentList, key = { it.watchbuddyID.toString() }) { movie ->
                MediaCard(
                    cardData = movie,
                    cardStyle = state.settings.cardStyle,
                    scoreFormat = state.settings.scoreFormat,
                    onClick = { navigateToDetails(movie) },
                    onLongClick = { viewModel.showEditDialogFor(movie) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}
