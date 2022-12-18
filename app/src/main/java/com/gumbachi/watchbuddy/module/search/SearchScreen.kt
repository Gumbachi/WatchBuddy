package com.gumbachi.watchbuddy.module.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.ui.components.ErrorDisplay
import com.gumbachi.watchbuddy.ui.components.LoadingDisplay
import com.gumbachi.watchbuddy.ui.components.appbars.WatchbuddySearchAppBar
import com.gumbachi.watchbuddy.ui.components.cards.MediaCard
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaEditDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.MediaSaveDialog
import com.gumbachi.watchbuddy.ui.components.dialogs.SearchFilterDialog
import com.gumbachi.watchbuddy.ui.screens.search.components.NothingFoundPrompt
import com.gumbachi.watchbuddy.utils.displaySnackbar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            WatchbuddySearchAppBar(
                hint = "Search Movies/Shows...",
                focusRequester = focusRequester,
                onSearchClick = {
                    viewModel.searchFor(it)
                    focusManager.clearFocus()
                },
                onFilterClick = { viewModel.showFilterDialog() },
                onBackClick = onBackClick
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        // Loading results
        if (state.loading) {
            LoadingDisplay()
            return@Scaffold
        }


        state.error?.let {
            ErrorDisplay(error = it)
            return@Scaffold
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            state.searchState?.let {
                if (it.results.isEmpty()) {
                    NothingFoundPrompt(query = it.query)
                    return@Column
                }
            }

            state.searchState?.let { search ->
                SearchResultsLabel(
                    unemphasizedText = "Results for ",
                    emphasizedText = search.query,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(state.settings.cardStyle.requiredWidth),
                    contentPadding = PaddingValues(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    state = lazyGridState
                ) {
                    items(search.results, key = { it.watchbuddyID.toString() }) { searchResult ->
                        MediaCard(
                            cardData = searchResult,
                            cardStyle = state.settings.cardStyle,
                            scoreFormat = state.settings.scoreFormat,
                            isSaved = searchResult.watchbuddyID in state.savedMediaIDs,
                            onClick = { navigateToDetails(searchResult) },
                            onLongClick = {

                                viewModel.showEditDialogFor(searchResult)
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }
        }
    }

    if (state.showFilterDialog) {

        var filter by remember { mutableStateOf(state.filter) }
        var defaultChecked by remember {  mutableStateOf(filter == state.settings.defaultSearchFilter) }

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
                onDelete = { viewModel.deleteMedia(media) {
                    displaySnackbar(
                        message = "Deleted ${media.watchbuddyID.type}",
                        actionLabel = "UNDO",
                        snackbarState = snackbarHostState
                    ) {
                        viewModel.saveMedia(media)
                    }
                } },

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

@Composable
fun SearchResultsLabel(
    unemphasizedText: String,
    emphasizedText: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        buildAnnotatedString {
            append(unemphasizedText)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(emphasizedText)
            }
        },
        textAlign = textAlign,
        style = style,
        modifier = modifier,
    )
}
