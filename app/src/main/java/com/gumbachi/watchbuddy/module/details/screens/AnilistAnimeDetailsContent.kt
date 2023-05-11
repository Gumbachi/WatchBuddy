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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.datasource.anilist.mappers.getPreferred
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistAnimeDetails
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.ui.cards.TitleMediaCard
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn
import com.gumbachi.watchbuddy.ui.details.components.DetailsBackdropBanner
import com.gumbachi.watchbuddy.ui.details.components.DetailsLazyCardRow
import com.gumbachi.watchbuddy.ui.details.components.DetailsOverviewSection
import com.gumbachi.watchbuddy.ui.details.components.DetailsPersonCard
import com.gumbachi.watchbuddy.ui.details.components.DetailsPosterAndDetails
import com.gumbachi.watchbuddy.ui.details.components.DetailsReviewItem
import com.gumbachi.watchbuddy.ui.details.components.ReviewDialog
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation

@Composable
fun AnilistAnimeDetailsContent(
    anime: AnilistAnimeDetails,
    preferredTitleLanguage: AnilistTitleLanguage,
    scoreFormat: ScoreFormat,
    onBackClicked: () -> Unit,
    navigateToDetails: (WatchBuddyID) -> Unit,
    modifier: Modifier = Modifier,
) {

    val lazyListState = rememberLazyListState()

    var focusedReview by remember { mutableStateOf<AnilistAnimeDetails.UserReview?>(null) }
    var focusedPoster by remember { mutableStateOf<String?>(null) }
    var focusedBackdrop by remember { mutableStateOf<String?>(null) }

    Box {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState,
            modifier = modifier.fillMaxSize()
        ) {
            item {
                Column {
                    DetailsBackdropBanner(
                        imageURL = anime.backdropURL,
                        onClick = { focusedBackdrop = anime.backdropURL }
                    )
                    ColorWrappedColumn(
                        innerPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp)
                    ) {
                        DetailsPosterAndDetails(
                            title = anime.titles.getPreferred(preferredTitleLanguage),
                            imageURL = anime.posterURL,
                            onPosterClick = { focusedPoster = anime.posterURL },
                            shortDetails = listOfNotNull(
                                "AniList ID: ${anime.id}",
                                anime.getFormattedDuration()?.let { "Runtime: $it" },
                                if (anime.mediaType == MediaType.Show) {
                                    anime.startDate?.let {
                                        "Aired: $it - ${anime.endDate?.toString() ?: "??"}"
                                    }
                                } else {
                                    anime.startDate?.let { "Released: $it" }
                                },
                                anime.getAverageScore(scoreFormat)?.let { "Average Score: $it" },
                                anime.genres.takeIf { it.isNotEmpty() }
                                    ?.let { "Genres: ${it.joinToString(", ")}" }
                            )
                        )
                    }
                }
            }

            item {
                ColorWrappedColumn() {
                    DetailsOverviewSection(
                        overview = anime.description,
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
                        title = "Characters",
                        items = anime.characters
                    ) {
                        DetailsPersonCard(
                            imageURL = it.posterURL,
                            contentDescription = it.name,
                            primaryDetail = it.name,
                            secondaryDetail = it.voiceActor
                        )
                    }
                    DetailsLazyCardRow(
                        title = "Staff",
                        items = anime.staff.distinct()
                    ) {
                        DetailsPersonCard(
                            imageURL = it.profileURL,
                            contentDescription = it.name,
                            primaryDetail = it.name,
                            secondaryDetail = it.occupations.joinToString(" / ")
                        )
                    }
                }
            }

            item {
                ColorWrappedColumn(innerPadding = PaddingValues(8.dp)) {
                    DetailsLazyCardRow(title = "Recommendations", items = anime.recommendations) {
                        TitleMediaCard(
                            posterURL = it.posterURL,
                            title = it.titles.getPreferred(preferredTitleLanguage),
                            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3),
                            modifier = Modifier.width(150.dp),
                            onClick = { navigateToDetails(WatchBuddyID(API.Anilist, it.mediaType, it.id)) }
                        )
                    }
                    if (anime.relations.isNotEmpty()) {
                        DetailsLazyCardRow(title = "Relations", items = anime.relations) {
                            TitleMediaCard(
                                posterURL = it.posterURL,
                                title = it.titles.getPreferred(preferredTitleLanguage),
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3),
                                modifier = Modifier.width(150.dp),
                                onClick = { navigateToDetails(WatchBuddyID(API.Anilist, it.mediaType, it.id)) }
                            )
                        }
                    }
                }
            }


            if (anime.reviews.isNotEmpty()) {
                item {
                    ColorWrappedColumn(innerPadding = PaddingValues(8.dp)) {
                        DetailsLazyCardRow(
                            title = "Reviews",
                            items = anime.reviews
                        ) {
                            DetailsReviewItem(
                                review = it,
                                modifier = Modifier.width(280.dp),
                                onClick = { focusedReview = it }
                            )
                        }
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

    // Dialogs
    focusedPoster?.let {
        Dialog(onDismissRequest = { focusedPoster = null }) {
            SubcomposeAsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
                error = { focusedPoster = null }
            )
        }
    }


    focusedBackdrop?.let {
        Dialog(onDismissRequest = { focusedBackdrop = null }) {
            SubcomposeAsyncImage(
                model = anime.backdropURL,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
                error = { focusedBackdrop = null }
            )
        }
    }


    focusedReview?.let {
        ReviewDialog(
            review = it,
            onDismissRequest = { focusedReview = null }
        )
    }
}