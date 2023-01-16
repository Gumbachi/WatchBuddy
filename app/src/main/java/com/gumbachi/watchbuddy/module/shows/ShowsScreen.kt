package com.gumbachi.watchbuddy.module.shows

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.ui.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.MediaTabRow
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.dialog.MediaEditDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaSortDialog
import com.gumbachi.watchbuddy.ui.toolbars.MediaAppBar
import org.koin.androidx.compose.koinViewModel

private const val TAG = "ShowsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowsViewModel = koinViewModel(),
    navigateToSearch: () -> Unit = {},
    navigateToDetails: (Show) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()
    val cardSettings = state.settings.card

    // Composables
    val snackbarHostState = remember { SnackbarHostState() }
    val gridState = rememberLazyGridState()


    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier,
        topBar = {
            MediaAppBar(
                title = "Shows",
                onSortClicked = viewModel::showSortDialog,
                onFilterClicked = { /* TODO */ }
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
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(cardSettings.style.requiredWidth),
            horizontalArrangement = Arrangement.Center,
            state = gridState,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(
                items = state.currentList,
                key = { it.watchbuddyID.toString() }
            ) { show ->

                val showScore = when (cardSettings.showScoreOnPlanned) {
                    true -> true
                    false -> show.watchStatus != WatchStatus.Planning
                }

                val showProgress = when (cardSettings.showProgressOnPlanned) {
                    true -> true
                    false -> show.watchStatus != WatchStatus.Planning
                }

                MediaCard(
                    cardData = show,
                    cardStyle = cardSettings.style,
                    scoreFormat = cardSettings.scoreFormat,
                    showApi = cardSettings.showApi,
                    showScore = showScore,
                    showProgress = showProgress,
                    onClick = { navigateToDetails(show) },
                    onLongClick = { viewModel.showEditDialogFor(show) },
                    onProgressClick = { viewModel.incrementShowProgress(show) }
                )
            }
        }
    }

    //region Dialogs
    MediaEditDialog(
        title = "Edit Show",
        isMediaSaved = true,
        media = state.showUnderEdit,
        scoreFormat = state.settings.card.scoreFormat,
        hiddenStatuses = state.settings.shows.hiddenStatuses,
        onCancel = { viewModel.hideEditDialog() },
        onDismissRequest = {},
        onMediaDelete = { viewModel.deleteShow(snackbarHostState) },
        onConfirm = { updatedMedia ->
            state.showUnderEdit?.let {
                viewModel.updateShow(
                    original = it,
                    updated = updatedMedia as Show,
                    snackbarState = snackbarHostState
                )
            }
        }
    )

    MediaSortDialog(
        showDialog = state.showSortDialog,
        title = "Sort Shows",
        defaultSort = state.settings.shows.sort,
        defaultOrder = state.settings.shows.sortOrder,
        onDismissRequest = viewModel::hideSortDialog,
        onConfirm = viewModel::updateShowSort
    )

    //endregion
}
