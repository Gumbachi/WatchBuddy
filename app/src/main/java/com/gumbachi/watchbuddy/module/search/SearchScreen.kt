package com.gumbachi.watchbuddy.module.search

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaEditDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaSaveDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.SearchFilterDialog
import com.gumbachi.watchbuddy.utils.displaySnackbar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    navigateToDetails: (SearchResult) -> Unit = {},
    navigateToWatchlist: (WatchbuddyID) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()

    val lazyGridState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    WatchbuddyScaffold(
        modifier = modifier,
        isLoading = state.loading,
        error = state.error,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Search/Discover") }
            )
        },
        dialogs = {
            if (state.showFilterDialog) {

                var filter by remember { mutableStateOf(state.filter) }
                var defaultChecked by remember { mutableStateOf(filter == state.settings.defaultSearchFilter) }

                SearchFilterDialog(
                    title = "Filter Results",
                    filter = filter,
                    onFilterChange = { filter = it },
                    defaultChecked = defaultChecked,
                    onDefaultCheckedChange = { defaultChecked = it },
                    onSubmit = { viewModel.onFilterUpdate(filter) }, // TODO Handle saving default
                    onCancel = { viewModel.hideFilterDialog() })
            }

            state.underEdit?.let { media ->
                if (media.watchbuddyID in state.savedMediaIDs) {
                    MediaEditDialog(
                        title = media.title,
                        editable = media,
                        scoreFormat = state.settings.scoreFormat,
                        onSubmit = {
                            viewModel.updateMedia(it) {
                                displaySnackbar(
                                    message = "Updated ${media.watchbuddyID.type}",
                                    actionLabel = "View",
                                    snackbarState = snackbarHostState
                                ) {
                                    viewModel.updateMedia(media)
                                }
                            }
                        },
                        onCancel = { viewModel.hideEditDialog() },
                        onDelete = {
                            viewModel.deleteMedia(media) {
                                displaySnackbar(
                                    message = "Deleted ${media.watchbuddyID.type}",
                                    actionLabel = "UNDO",
                                    snackbarState = snackbarHostState
                                ) {
                                    viewModel.saveMedia(media)
                                }
                            }
                        },

                        )
                } else {
                    MediaSaveDialog(
                        title = media.title,
                        editable = media,
                        scoreFormat = state.settings.scoreFormat,
                        onCancel = viewModel::hideEditDialog,
                        onSubmit = {
                            viewModel.saveMedia(it) {
                                displaySnackbar(
                                    message = "Added ${media.watchbuddyID.type}",
                                    actionLabel = "View",
                                    snackbarState = snackbarHostState
                                ) {
                                    navigateToWatchlist(media.watchbuddyID)
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {

    }
}


//OLD
//state.searchState?.let {
//    if (it.results.isEmpty()) {
//        NothingFoundPrompt(query = it.query)
//    }
//}
//
//state.searchState?.let { search ->
//    SearchResultLabel(
//        unemphasizedText = "Results for ",
//        emphasizedText = search.query,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
//    )
//
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(state.settings.cardStyle.requiredWidth),
//        contentPadding = PaddingValues(vertical = 4.dp),
//        horizontalArrangement = Arrangement.Center,
//        state = lazyGridState
//    ) {
//        items(search.results, key = { it.watchbuddyID.toString() }) { searchResult ->
//            MediaCard(
//                cardData = searchResult,
//                cardStyle = state.settings.cardStyle,
//                scoreFormat = state.settings.scoreFormat,
//                isSaved = searchResult.watchbuddyID in state.savedMediaIDs,
//                onClick = { navigateToDetails(searchResult) },
//                onLongClick = { viewModel.showEditDialogFor(searchResult) },
//                modifier = Modifier.animateItemPlacement()
//            )
//        }
//    }
//}