package com.gumbachi.watchbuddy.module.details.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.ui.cards.TitleMediaCard
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn
import com.gumbachi.watchbuddy.ui.details.components.DetailsBackdropBanner
import com.gumbachi.watchbuddy.ui.details.components.DetailsClickCarousel
import com.gumbachi.watchbuddy.ui.details.components.DetailsImageCarouselDialog
import com.gumbachi.watchbuddy.ui.details.components.DetailsLazyCardRow
import com.gumbachi.watchbuddy.ui.details.components.DetailsOverviewSection
import com.gumbachi.watchbuddy.ui.details.components.DetailsPersonCard
import com.gumbachi.watchbuddy.ui.details.components.DetailsPosterAndDetails
import com.gumbachi.watchbuddy.ui.details.components.DetailsReviewItem
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation

@Composable
fun TMDBMovieDetailsContent(
    movie: TMDBMovieDetails,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetails: (WatchBuddyID) -> Unit = {}
) {

    val lazyListState = rememberLazyListState()
    var showBackdropImageCarousel by remember { mutableStateOf(false) }
    var showPosterImageCarousel by remember { mutableStateOf(false) }

    Box {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState,
            modifier = modifier.fillMaxSize()
        ) {
            item {
                Column {
                    DetailsBackdropBanner(
                        imageURL = movie.backdropURL,
                        onClick = { showBackdropImageCarousel = true }
                    )
                    ColorWrappedColumn(
                        innerPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp)
                    ) {
                        DetailsPosterAndDetails(
                            title = movie.title,
                            tagline = movie.tagline,
                            imageURL = movie.posterURL,
                            shortDetails = listOf(
                                "TMDB ID: ${movie.id}",
                                "Runtime: ${movie.formattedRuntime}",
                                "Release Date: ${movie.releaseDate ?: "Unknown"}",
                                "Average Score: ${movie.globalScore}",
                                "Budget: ${movie.budget}",
                                "Revenue: ${movie.revenue}",
                                "Genres: ${movie.genres.joinToString(", ") { it.name }}"
                            ),
                            onPosterClick = { showPosterImageCarousel = true }
                        )
                    }
                }
            }

            item {
                ColorWrappedColumn() {
                    DetailsOverviewSection(
                        overview = movie.overview,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            item {
                ColorWrappedColumn(
                    innerPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailsLazyCardRow(
                        title = "Cast",
                        items = movie.cast
                    ) {
                        DetailsPersonCard(
                            imageURL = it.profileURL,
                            contentDescription = it.name,
                            primaryDetail = it.name,
                            secondaryDetail = it.character
                        )
                    }

                    val consolidatedCrew = remember {
                        movie.crew.groupBy { it.id }.values.map {
                            object {
                                val name = it.first().name
                                val profileURL = it.first().profileURL
                                val job = it.joinToString(", ") { it.job }
                                val popularity = it.first().popularity
                            }
                        }.sortedByDescending { it.popularity }
                    }

                    DetailsLazyCardRow(
                        title = "Crew",
                        items = consolidatedCrew
                    ) {
                        DetailsPersonCard(
                            imageURL = it.profileURL,
                            contentDescription = it.name,
                            primaryDetail = it.name,
                            secondaryDetail = it.job
                        )
                    }
                }
            }

            item {
                ColorWrappedColumn(innerPadding = PaddingValues(8.dp)) {
                    DetailsLazyCardRow(title = "Recommendations", items = movie.recommendations) {
                        TitleMediaCard(
                            posterURL = it.posterURL,
                            title = it.title,
                            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3),
                            modifier = Modifier.width(150.dp),
                            onClick = { navigateToDetails(WatchBuddyID(API.TMDB, MediaType.Movie, it.id)) }
                        )
                    }
                }
            }

            item {
                ColorWrappedColumn(innerPadding = PaddingValues(8.dp)) {
                    DetailsClickCarousel(title = "Reviews", items = movie.reviews) {
                        DetailsReviewItem(
                            name = it.author,
                            content = it.content,
                            avatarURL = it.avatarURL,
                            rating = it.rating?.toString(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            FilledIconButton(
                onClick = onBackClicked, colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.4F),
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
            }
        }
    }

    // Backdrop Image Carousel
    DetailsImageCarouselDialog(
        shown = showBackdropImageCarousel,
        imageURLs = movie.backdrops.map { it.url },
        onDismissRequest = { showBackdropImageCarousel = false }
    )

    // Poster Image Carousel
    DetailsImageCarouselDialog(
        shown = showPosterImageCarousel,
        imageURLs = movie.posters.map { it.url },
        onDismissRequest = { showPosterImageCarousel = false }
    )

}
