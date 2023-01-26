package com.gumbachi.watchbuddy.module.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.module.details.screens.TMDBMovieDetailsContent
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.details.components.DetailsPosterAndDetails
import com.gumbachi.watchbuddy.ui.details.components.DetailsTitle
import com.gumbachi.watchbuddy.ui.toolbars.WatchbuddyBackAppBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDetailsScreen(
    watchbuddyID: WatchBuddyID,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(),
    onBackClicked: () -> Unit = {},
) {

    val state by viewModel.uiState.collectAsState()

    // Load details
    LaunchedEffect(Unit) {
        viewModel.loadDetailsFor(watchbuddyID)
    }

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        topBar = {
            WatchbuddyBackAppBar(
                title = "${watchbuddyID.type} Details",
                onBackClicked = onBackClicked
            )
        },
    ) {

        when (val details = state.details) {
            is TMDBMovieDetails -> TMDBMovieDetailsContent(movie = details)
            null -> {}
            else -> {
                DetailsTitle(title = details.title)
                DetailsPosterAndDetails(
                    imageURL = details.posterURL,
                    shortDetails = listOf("No Details Provided")
                )
            }
        }
    }
}
