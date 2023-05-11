package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gumbachi.watchbuddy.model.interfaces.Review
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    model = review.avatarURL,
                    loading = {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Default Avatar"
                        )
                    },
                    contentDescription = "${review.author}'s avatar",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = when {
                        review.rating != null -> "${review.author}  •  ${review.rating}"
                        else -> review.author
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.height(180.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsReviewItem(
    name: String,
    content: String,
    avatarURL: String,
    modifier: Modifier = Modifier,
    rating: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    model = avatarURL,
                    loading = {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Default Avatar"
                        )
                    },
                    contentDescription = "$name's avatar",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = when {
                        rating != null -> "$name  •  $rating"
                        else -> name
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.height(180.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            DetailsReviewItem(
                name = "Reviewer #123",
                rating = "83%",
                content = LoremIpsum().values.joinToString(),
                avatarURL = ""
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    WatchBuddyTheme(darkTheme = false) {
        Surface {
            DetailsReviewItem(
                name = "Reviewer #123",
                content = "Hi, This is a review of X item I thought it was decent",
                avatarURL = ""
            )
        }
    }
}