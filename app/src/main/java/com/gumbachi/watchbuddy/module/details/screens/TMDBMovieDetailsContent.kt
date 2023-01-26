package com.gumbachi.watchbuddy.module.details.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn
import com.gumbachi.watchbuddy.ui.details.components.*
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun TMDBMovieDetailsContent(
    movie: TMDBMovieDetails,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                DetailsBackdropBanner(imageURL = movie.backdropURL)
                ColorWrappedColumn {
                    DetailsTitle(title = movie.title, modifier = Modifier.padding(bottom = 12.dp))
                    DetailsPosterAndDetails(
                        imageURL = movie.posterURL,
                        shortDetails = listOf(
                            "TMDB ID: ${movie.id}",
                            "Runtime: ${movie.formattedRuntime}",
                            "Release Date: ${movie.releaseDate ?: "Unknown"}",
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            item {
                movie.overview?.let { overview ->
                    ColorWrappedColumn {
                        DetailsOverviewSection(
                            overview = overview,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            item {
                ColorWrappedColumn(padding = PaddingValues(8.dp)) {
                    Text(text = "Cast", style = MaterialTheme.typography.titleMedium)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(movie.cast) {
                            DetailsCreditCard(
                                imageURL = it.profileURL,
                                contentDescription = it.name,
                                primaryDetail = it.name,
                                secondaryDetail = it.character
                            )
                        }
                    }
                }
            }

            item {
                ColorWrappedColumn {
                    LazyRow {
                        items(movie.crew) {
                            DetailsCreditCard(
                                imageURL = it.profileURL,
                                contentDescription = it.name,
                                primaryDetail = it.name,
                                secondaryDetail = it.job
                            )
                        }
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = false) {
        Surface {

        }
    }
}