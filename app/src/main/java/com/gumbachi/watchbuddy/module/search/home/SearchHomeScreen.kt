package com.gumbachi.watchbuddy.module.search.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.search.components.RecentSearchesSection
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchHomeScreen(
    navigateToMediaSearch: () -> Unit,
    navigateToDetails: (WatchBuddyID) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchHomeViewModel = koinViewModel(),
) {

    val state by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    WatchbuddyScaffold(
        modifier = modifier,
        isLoading = state.loading,
        error = state.error,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = { Text("Search Home") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                FilledTonalButton(
                    onClick = { navigateToMediaSearch() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Go To Search Page")
                    Text(text = "Search Movies/Shows...")
                }
            }
        }
    ) {
        RecentSearchesSection(
            recents = state.recentItems,
            onItemClick = { navigateToDetails(it.watchbuddyID) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
