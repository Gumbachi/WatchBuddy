package com.gumbachi.watchbuddy.ui.components.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.utils.toString

val statusLine = Modifier
    .width(3.dp)
    .fillMaxHeight()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompactMediaCard(
    imageURL: String,
    headline: String,
    primarySubtitle: String,
    secondarySubtitle: String,
    score: String,
    statusColor: Color,
    modifier: Modifier = Modifier,
    progress: String? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(105.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            PosterImage(
                posterURL = imageURL,
                matchConstraintByHeight = true
            )
            Spacer(modifier = statusLine.background(statusColor))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = headline,
                    maxLines = 2,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    Text(
                        text = primarySubtitle,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = secondarySubtitle,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .offset(x = (-5).dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(end = 13.dp, top = 5.dp, bottom = 5.dp)
                            .offset(x = 5.dp)

                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(13.dp)
                                    .padding(1.dp)
                            )
                            Text(
                                text = score,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }

                        progress?.let {
                            Text(
                                text = progress,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DefaultCompactMediaCardPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface(modifier = Modifier.padding(16.dp)) {
            CompactMediaCard(
                imageURL = "",
                headline = "Movie or Show Title",
                primarySubtitle = "1h 24m",
                secondarySubtitle = "2021-05-27",
                score = "10",
                statusColor = Color.Blue,
                progress = "50 / 128",
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun CompactMediaCardPreview() {
    DefaultCompactMediaCardPreview(darkMode = true)
}
