package com.gumbachi.watchbuddy.module.details.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.ui.cards.TitleMediaCard
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn
import com.gumbachi.watchbuddy.ui.details.components.*
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation

@Composable
fun TMDBMovieDetailsContent(
    movie: TMDBMovieDetails,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
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
                    DetailsLazyCardRow(
                        title = "Crew",
                        items = movie.crew
                            .sortedByDescending { it.popularity }
                            .distinctBy { it.name }
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
                            modifier = Modifier.width(150.dp)
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
                            avatarURL = it.authorAvatarURL,
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
