package com.gumbachi.watchbuddy.ui.app.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.format
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun MediaCard(
    searchResult: SearchResult,
    modifier: Modifier = Modifier,
    scoreFormat: ScoreFormat = ScoreFormat.Decimal,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    searchResult.apply {
        MediaCard(
            imageURL = posterURL,
            headline = title,
            primarySubtitle = primaryDetail,
            secondarySubtitle = secondaryDetail,
            score = averageScore.format(scoreFormat),
            statusText = releaseStatus.text,
            statusColor = releaseStatus.color,
            onClick = onClick,
            onLongClick = onLongClick,
            modifier = modifier
        )
    }
}

@Composable
fun MediaCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    scoreFormat: ScoreFormat = ScoreFormat.Decimal
) {
    movie.apply {
        MediaCard(
            imageURL = posterURL,
            headline = title,
            primarySubtitle = movie.runtime,
            secondarySubtitle = movie.releaseDate.toString(),
            score = userScore.format(scoreFormat),
            statusText = releaseStatus.text,
            statusColor = releaseStatus.color,
            onClick = onClick,
            onLongClick = onLongClick,
            modifier = modifier
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaCard(
    imageURL: String,
    headline: String,
    primarySubtitle: String,
    secondarySubtitle: String,
    score: String,
    statusText: String,
    statusColor: Color,
    modifier: Modifier = Modifier,
    progress: String? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier=modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {


        Box {
            // Top Portion of Card
            PosterImage(posterURL = imageURL)

            // Score Bubble
            CardBubble(
                text = score,
                shape = RoundedCornerShape(topStart = 10.dp),
                innerPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Filled.Star
            )

            // Progress Bubble
            progress?.let {
                CardBubble(
                    text = it,
                    shape = RoundedCornerShape(topEnd = 10.dp),
                    innerPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }

        // Bottom Portion of card
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = headline,
                maxLines = 1,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = primarySubtitle,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = secondarySubtitle,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                StatusBubble(
                    text = statusText,
                    color = statusColor
                )
            }
        }
    }
}

@Composable
private fun CardBubble(
    text: String,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    shape: Shape = RoundedCornerShape(10.dp),
    icon: ImageVector? = null
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(shape = shape)
            .background(color = Color.Black.copy(alpha = 0.7f))
            .padding(innerPadding)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(14.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = Color.White
        )
    }
}

@Composable
private fun StatusBubble(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        maxLines = 1,
        color = Color.White,
        modifier = modifier
            .clip(CircleShape)
            .background(color = color)
            .padding(horizontal = 8.dp, vertical = 3.dp)

    )
}


@Preview
@Composable
private fun DefaultMediaCardPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                imageURL = "",
                headline = "Movie or Show Title",
                primarySubtitle = "1h 24m",
                secondarySubtitle = "2021-05-27",
                score = "10",
                statusText = "Released",
                statusColor = Color.Blue,
                progress = "50 / 128"
            )
        }
    }
}

@Preview
@Composable
private fun MediaCardPreview() {
    DefaultMediaCardPreview(darkMode = true)
}