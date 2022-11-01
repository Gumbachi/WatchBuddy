package com.gumbachi.watchbuddy.ui.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

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
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier=modifier.padding(horizontal = 7.dp, vertical = 5.dp)
    ) {


        Box {
            // Top Portion of Card
            SubcomposeAsyncImage(
                model = imageURL,
                loading = {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.scale(2F))
                    }
                },
                error = {
                    Image(
                        imageVector = Icons.Filled.BrokenImage,
                        contentDescription = "Image failed to load"
                    )
                },
                contentDescription = "$headline Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.aspectRatio(0.66F)
            )

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
                    innerPadding = PaddingValues(horizontal = 15.dp, vertical = 4.dp),
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
                    .padding(bottom = 5.dp)
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
fun CardBubble(
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
fun StatusBubble(
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
fun DefaultMediaCardPreview(darkMode: Boolean = false) {
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
fun MediaCardPreview() {
    DefaultMediaCardPreview(darkMode = true)
}