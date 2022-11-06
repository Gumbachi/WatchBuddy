package com.gumbachi.watchbuddy.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.ui.app.components.PosterImage
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun DetailsScreen(
    api: SourceAPI,
    mediaType: MediaType,
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadDetails(api, mediaType, id)
    }

    if (state.loading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    state.details?.let {
        DetailsScreenTop(
            title = it.title,
            posterURL = it.posterURL,
            backdropURL = it.backdropURL,
            lines = it.shortDetails(),
            overview = it.overview
        )
    }
}


@Composable
fun DetailsScreenTop(
    title: String,
    posterURL: String,
    backdropURL: String,
    lines: List<String>,
    modifier: Modifier = Modifier,
    overview: String? = null,
) {
    Column(modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {

            Column(modifier = modifier.padding(4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PosterImage(
                        posterURL = posterURL,
                        matchConstraintByHeight = true,
                        modifier = Modifier
                            .height(220.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column() {
                        lines.forEach {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }

        overview?.let {
            // Overview Section
            Text(
                text = "Overview",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = overview,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun DetailsContentPreview() {
    WatchBuddyTheme {
        Surface {
            DetailsScreenTop(
                title = "Movie Title",
                posterURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fyy1nDC8wm553FCiBDojkJmKLCs.jpg",
                backdropURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fyy1nDC8wm553FCiBDojkJmKLCs.jpg",
                lines = listOf(
                    "Average Score: 8.9",
                    "Average Score: 8.9",
                    "Average Score: 8.9",
                    "Average Score: 8.9",
                    "Average Score: 8.9",
                    "Average Score: 8.9",
                ),
                overview = "This is where the overview goes"
            )
        }
    }
}
