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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.ui.components.MediaTabRow
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.components.appbars.MediaAppBar
import com.gumbachi.watchbuddy.ui.components.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaEditDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaSortDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    // Composables
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()


    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier,
        topBar = {
            MediaAppBar(
                title = "Shows",
                onSortClicked = navigateToSearch,
                onFilterClicked = viewModel::showSortDialog
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
            if (state.showSortDialog) {
                MediaSortDialog(
                    title = "Sort Shows By",
                    defaultSort = state.settings.showSort,
                    onSortChange = { newSort ->
                        scope.launch {
                            viewModel.updateShowSortTo(newSort)
                            delay(400L)
                            gridState.animateScrollToItem(0)
                        }
                    },
                )
            }

            state.showUnderEdit?.let { show ->
                MediaEditDialog(
                    title = show.title,
                    editable = show,
                    scoreFormat = state.settings.scoreFormat,
                    onCancel = { viewModel.hideEditDialog() },
                    onDelete = { viewModel.deleteShow(snackbarState = snackbarHostState) },
                    onSubmit = {
                        viewModel.updateShow(
                            original = show,
                            updated = it as Show,
                            snackbarState = snackbarHostState
                        )
                    }
                )
            }
        },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(state.settings.cardStyle.requiredWidth),
            horizontalArrangement = Arrangement.Center,
            state = gridState,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(state.currentList, key = { it.watchbuddyID.toString() }) { show ->
                MediaCard(
                    cardData = show,
                    cardStyle = state.settings.cardStyle,
                    scoreFormat = state.settings.scoreFormat,
                    onClick = { navigateToDetails(show) },
                    onLongClick = { viewModel.showEditDialogFor(show) }
                )
            }
        }
    }
}
