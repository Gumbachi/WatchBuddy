package com.gumbachi.watchbuddy.ui.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

val statusLine = Modifier.width(3.dp).fillMaxSize()


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
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(105.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
        ) {
            CardImage(
                imageURL = imageURL,
                aspectRatio = 0.6F,
            )
            Spacer(modifier = statusLine.background(statusColor))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
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
                                modifier = Modifier.size(13.dp).padding(1.dp)
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

@Composable
fun CardImage(
    imageURL: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    aspectRatio: Float = 0.75F,
    matchConstraintByHeight: Boolean = true
) {
    SubcomposeAsyncImage(
        model = imageURL,
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
        error = {
            Image(
                imageVector = Icons.Filled.BrokenImage,
                contentDescription = "Image failed to load"
            )
        },
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        modifier = modifier.aspectRatio(aspectRatio, matchConstraintByHeight)

    )
}

@Preview
@Composable
fun DefaultCompactMediaCardPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface(modifier = Modifier.padding(16.dp)) {
            CompactMediaCard(
                imageURL = "",
                headline = "Movie or Show Title",
                primarySubtitle = "1h 24m",
                secondarySubtitle = "2021-05-27",
                score = "10",
                statusColor = Color.Blue,
                progress = "50 / 128"
            )
        }
    }
}

@Preview
@Composable
fun CompactMediaCardPreview() {
    DefaultCompactMediaCardPreview(darkMode = true)
}
