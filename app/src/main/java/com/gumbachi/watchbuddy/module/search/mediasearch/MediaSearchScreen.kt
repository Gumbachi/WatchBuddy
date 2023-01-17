package com.gumbachi.watchbuddy.module.search.mediasearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.ui.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.dialog.FilterDialog
import com.gumbachi.watchbuddy.ui.dialog.MediaEditDialog
import com.gumbachi.watchbuddy.ui.screens.search.SearchPrompt
import com.gumbachi.watchbuddy.ui.screens.search.SearchResultLabel
import com.gumbachi.watchbuddy.ui.snackbars.showUndoSnackbar
import com.gumbachi.watchbuddy.ui.snackbars.showViewMediaSnackbar
import com.gumbachi.watchbuddy.ui.toolbars.WatchBuddySearchAppBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: MediaSearchViewModel = koinViewModel(),
    navigateToSearchHome: () -> Unit = {},
    navigateToDetails: (id: WatchBuddyID) -> Unit = {},
    navigateToMedia: (Media) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()
    val cardSettings = state.settings.card
    val movieSettings = state.settings.movies
    val showSettings = state.settings.shows


    val gridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        // If returning from details, don't pull the OSK up
        if (state.searchState == null) {
            focusRequester.requestFocus()
        }
    }

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        snackbarHost = { SnackbarHost(hostState = snackbar) },
        topBar = {
            WatchBuddySearchAppBar(
                hint = "Search Movies/Shows",
                onBack = navigateToSearchHome,
                onSearch = {
                    viewModel.searchFor(it)
                    focusManager.clearFocus()
                },
                onFilterClick = viewModel::showFilterDialog,
                focusRequester = focusRequester
            )
        },
        modifier = modifier
    ) {


        state.searchState?.let {
            SearchResultLabel(
                unemphasizedText = "Results for ",
                emphasizedText = state.searchState!!.query,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(state.settings.card.style.requiredWidth),
                contentPadding = PaddingValues(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                state = gridState
            ) {
                items(
                    state.searchState!!.results,
                    key = { it.watchbuddyID.toString() }) { searchResult ->
                    MediaCard(
                        cardData = searchResult,
                        cardStyle = state.settings.card.style,
                        scoreFormat = state.settings.card.scoreFormat,
                        isSaved = searchResult.watchbuddyID in state.savedMediaIDs,
                        onClick = {
                            navigateToDetails(searchResult.watchbuddyID)
                            viewModel.addItemToRecents(searchResult)
                        },
                        onLongClick = {
                            viewModel.showEditDialogFor(searchResult)
                            viewModel.addItemToRecents(searchResult)
                        },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }

        if (state.searchState == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8F)
            ) {
                SearchPrompt(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .imePadding()
                )
            }
        }
    }

    FilterDialog(
        showDialog = state.showFilterDialog,
        title = "Filter Search",
        initialFilter = state.filter,
        onConfirm = viewModel::updateFilter,
        onDismissRequest = viewModel::hideFilterDialog
    )

    // Edit/Save Dialog
    state.underEdit?.let { media ->

        val hiddenStatuses = when (media.type) {
            MediaType.Movie -> movieSettings.hiddenStatuses
            MediaType.Show -> showSettings.hiddenStatuses
        }

        val isSaved = media.watchbuddyID in state.savedMediaIDs

        media.apply {
            if (watchStatus in hiddenStatuses && !isSaved) {
                watchStatus = (WatchStatus.values - hiddenStatuses).first()
            }
        }

        MediaEditDialog(
            title = if (isSaved) "Update Media" else "Save Media",
            isMediaSaved = isSaved,
            media = media,
            scoreFormat = cardSettings.scoreFormat,
            hiddenStatuses = hiddenStatuses,
            showProgressSection = media.isEpisodic,
            onDismissRequest = viewModel::hideEditDialog,
            onMediaDelete = {
                viewModel.deleteMedia(media)
                snackbar.showUndoSnackbar(
                    scope = scope,
                    message = "Deleted ${media.type}",
                    undo = { viewModel.saveMedia(media) }
                )
            },
            onConfirm = {
                if (isSaved) viewModel.updateMedia(it) else viewModel.saveMedia(it)
                val message = if (isSaved) "Updated ${media.type}" else "Saved ${media.type}"
                snackbar.showViewMediaSnackbar(
                    scope = scope,
                    message = message,
                    navigateToMedia = { navigateToMedia(it) }
                )
            }
        )
    }
}