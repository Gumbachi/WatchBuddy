package com.gumbachi.watchbuddy.module.shows

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.ui.cards.LazyMediaGrid
import com.gumbachi.watchbuddy.ui.components.MediaTabRow
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.dialog.MediaEditDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaSortDialog
import com.gumbachi.watchbuddy.ui.toolbars.MediaAppBar
import org.koin.androidx.compose.koinViewModel

private const val TAG = "ShowsScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
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

    val pagerState = rememberPagerState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            println("Refresh")
            viewModel.refresh()
        }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.changeWatchStatusTab(page)
        }
    }


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

        Box(modifier = Modifier.pullRefresh(pullRefreshState, true)) {
            HorizontalPager(
                count = state.shownTabs.size,
                state = pagerState
            ) { page ->
                LazyMediaGrid(
                    items = state.watchlist.getListFor(state.shownTabs[page]),
                    settings = cardSettings,
                    onItemClick = { navigateToDetails(it as Show) },
                    onItemLongClick = { viewModel.showEditDialogFor(it as Show) }
                )
            }

            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }



//        LazyVerticalGrid(
//            columns = GridCells.Adaptive(cardSettings.style.requiredWidth),
//            horizontalArrangement = Arrangement.Center,
//            state = gridState,
//            contentPadding = PaddingValues(4.dp)
//        ) {
//            items(
//                items = state.currentList,
//                key = { it.watchbuddyID.toString() }
//            ) { show ->
//
//                val showScore = when (cardSettings.showScoreOnPlanned) {
//                    true -> true
//                    false -> show.watchStatus != WatchStatus.Planning
//                }
//
//                val showProgress = when (cardSettings.showProgressOnPlanned) {
//                    true -> true
//                    false -> show.watchStatus != WatchStatus.Planning
//                }
//
//                MediaCard(
//                    cardData = show,
//                    cardStyle = cardSettings.style,
//                    scoreFormat = cardSettings.scoreFormat,
//                    showApi = cardSettings.showApi,
//                    showScore = showScore,
//                    showProgress = showProgress,
//                    onClick = { navigateToDetails(show) },
//                    onLongClick = { viewModel.showEditDialogFor(show) },
//                    onProgressClick = { viewModel.incrementShowProgress(show) }
//                )
//            }
//        }
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
