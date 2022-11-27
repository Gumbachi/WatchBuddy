package com.gumbachi.watchbuddy.ui.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gumbachi.watchbuddy.components.appbars.WatchbuddySearchAppBar
import com.gumbachi.watchbuddy.components.cards.CompactMediaCard
import com.gumbachi.watchbuddy.components.cards.MediaCard
import com.gumbachi.watchbuddy.components.dialogs.MediaSaveDialog
import com.gumbachi.watchbuddy.components.dialogs.SearchScreenFilterDialog
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.ui.screens.search.components.NothingFoundPrompt
import com.gumbachi.watchbuddy.ui.screens.search.components.RecentSearchesList

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    navigateToDetails: (SearchResult) -> Unit = {},
    navigateToWatchlist: () -> Unit = {}
) {

    val state = viewModel.state
    val settings = state.settings

    val lazyGridState = rememberLazyGridState()
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
                    viewModel.onSearch(it)
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.searchResults.isEmpty()) {
                when {
                    state.currentSearch != null -> {
                        NothingFoundPrompt(query = state.currentSearch)
                    }

                    state.recentSearches.isNotEmpty() -> {
                        RecentSearchesList(searches = state.recentSearches)
                    }
                }
                return@Column
            }

            state.currentSearch?.let { query ->
                SearchResultsLabel(
                    unemphasizedText = "Results for ",
                    emphasizedText = query,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(settings.cardStyle.requiredWidth),
                contentPadding = PaddingValues(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                state = lazyGridState
            ) {
                items(state.searchResults, key = { it.id }) {
                    when (state.settings.cardStyle) {
                        CardStyle.Normal -> {
                            MediaCard(
                                searchResult = it,
                                onClick = { navigateToDetails(it) },
                                onLongClick = { viewModel.showEditDialogFor(it) },
                                scoreFormat = settings.scoreFormat,
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                        CardStyle.Compact -> {
                            CompactMediaCard(
                                searchResult = it,
                                onClick = { navigateToDetails(it) },
                                onLongClick = { viewModel.showEditDialogFor(it) },
                                scoreFormat = settings.scoreFormat,
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                    }
                }
            }
        }
    }



    if (state.showFilterDialog) {
        SearchScreenFilterDialog(
            defaultFilter = state.filter,
            onSubmit = {
                viewModel.hideFilterDialog()
                viewModel.onFilterUpdate(it)
            },
        )
    }

    state.underEdit?.let {
        MediaSaveDialog(
            title = state.underEdit.title,
            searchResult = state.underEdit,
            scoreFormat = settings.scoreFormat,
            onCancel = viewModel::hideEditDialog,
            onSubmit = {
                viewModel.hideEditDialog()
                viewModel.onMediaSave(state.underEdit, it)
            }
        )
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
