package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.cards.PosterImage
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun DetailsPosterAndDetails(
    imageURL: String,
    title: String,
    shortDetails: List<String>,
    modifier: Modifier = Modifier,
    tagline: String? = null,
    onPosterClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PosterImage(
            posterURL = imageURL,
            matchConstraintByHeight = false,
            modifier = Modifier
                .width(170.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onPosterClick() }
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            tagline?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Divider()
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                shortDetails.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            DetailsPosterAndDetails(
                imageURL = "",
                title = "Title that may be rather long but isnt aha",
                shortDetails = List(5) { "Howdy" },
                tagline = "Tagline Tagline Tagline"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    WatchBuddyTheme(darkTheme = false) {
        Surface {
            DetailsPosterAndDetails(
                imageURL = "",
                title = "Title that may be rather long but isnt aha",
                shortDetails = List(10) { "Howdy" }
            )
        }
    }
}