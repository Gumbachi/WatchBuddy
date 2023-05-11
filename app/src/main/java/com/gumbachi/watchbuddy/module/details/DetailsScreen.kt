package com.gumbachi.watchbuddy.module.details

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistAnimeDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowDetails
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.module.details.screens.AnilistAnimeDetailsContent
import com.gumbachi.watchbuddy.module.details.screens.TMDBMovieDetailsContent
import com.gumbachi.watchbuddy.module.details.screens.TMDBShowDetailsContent
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.details.components.DetailsPosterAndDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewDetailsScreen(
    watchbuddyID: WatchBuddyID,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    navigateToDetails: (WatchBuddyID) -> Unit,
    viewModel: DetailsViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDetailsFor(watchbuddyID)
    }

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier,
    ) {
        when (val details = state.details) {
            is TMDBMovieDetails -> TMDBMovieDetailsContent(
                movie = details,
                onBackClicked = onBackClicked,
                navigateToDetails = navigateToDetails
            )

            is TMDBShowDetails -> TMDBShowDetailsContent(
                show = details,
                onBackClicked = onBackClicked,
                navigateToDetails = navigateToDetails
            )

            is AnilistAnimeDetails -> AnilistAnimeDetailsContent(
                anime = details,
                onBackClicked = onBackClicked,
                preferredTitleLanguage = AnilistTitleLanguage.English, // TODO Fix this
                scoreFormat = ScoreFormat.Percentage, // TODO Fix this,
                navigateToDetails = navigateToDetails
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
