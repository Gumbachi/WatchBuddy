package com.gumbachi.watchbuddy.module.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.module.details.screens.TMDBMovieDetailsContent
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.details.components.DetailsPosterAndDetails
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

    val appBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )


    // Load details
    LaunchedEffect(Unit) {
        viewModel.loadDetailsFor(watchbuddyID)
    }

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier,
//        topBar = {
//            WatchBuddyBackAppBar(
//                title = "",
//                onBackClicked = onBackClicked,
//                scrollBehavior = null
//            )
//        }
    ) {
        when (val details = state.details) {
            is TMDBMovieDetails -> TMDBMovieDetailsContent(
                movie = details,
                onBackClicked = onBackClicked
            )
            null -> {}
            else -> {
                DetailsPosterAndDetails(
                    title = details.title,
                    imageURL = details.posterURL,
                    shortDetails = listOf("No Details Provided")
                )
                Button(onClick = onBackClicked) {
                    Text(text = "GO BACK")
                }
            }
        }
    }
}
