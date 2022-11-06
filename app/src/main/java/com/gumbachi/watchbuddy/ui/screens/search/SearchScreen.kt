package com.gumbachi.watchbuddy.ui.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.ui.app.components.CompactMediaCard
import com.gumbachi.watchbuddy.ui.app.components.MediaCard
import com.gumbachi.watchbuddy.ui.screens.search.components.AppSearchBar
import com.gumbachi.watchbuddy.ui.screens.search.components.SearchScreenFilterDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
    onBackClicked: () -> Unit = {},
    navigateToDetails: (SearchResult) -> Unit = {}
) {

    val state = viewModel.state

    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.updateUserSettings()
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        ) {
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onBackClicked()
                }
            ) { Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back") }

            AppSearchBar(
                hint = "Search for Movies/Shows...",
                focusRequester = focusRequester,
                onSearch = {
                    focusManager.clearFocus()
                    viewModel.onSearch(it)
                    scope.launch { lazyGridState.scrollToItem(0) }
                },
            )

            IconButton(onClick = { viewModel.onFilterButtonClick(false) }) {
                Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Filter Results")
            }

            if (state.showFilters)
                SearchScreenFilterDialog(
                    onDismissRequest = { viewModel.onFilterButtonClick(false) },
                    filterState = state.filterState,
                    onFilterStateChange = { viewModel.updateFilterState(it) },
                )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(state.cardSize.columns),
            contentPadding = PaddingValues(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
            state = lazyGridState
        ) {

            state.currentSearch?.let { query ->
                items(1) {
                    SearchResultsLabel(
                        unemphasizedText = "Results for ",
                        emphasizedText = query,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                    )
                }
            }

            state.searchResults?.let { searchResults ->
                items(searchResults) {
                    when (state.cardSize) {
                        CardStyle.Normal -> {
                            MediaCard(
                                searchResult = it,
                                onClick = { navigateToDetails(it) },
                                onLongClick = { /* TODO */ },
                                scoreFormat = state.scoreFormat
                            )
                        }
                        CardStyle.Compact -> {
                            CompactMediaCard(
                                searchResult = it,
                                onClick = { navigateToDetails(it) },
                                onLongClick = { /* TODO */ },
                                scoreFormat = state.scoreFormat
                            )
                        }
                    }
                }
            }
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
