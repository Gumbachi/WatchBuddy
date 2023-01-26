package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.cards.PosterImage

@Composable
fun DetailsPosterAndDetails(
    imageURL: String,
    shortDetails: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PosterImage(
            posterURL = imageURL,
            matchConstraintByHeight = false,
            modifier = Modifier
                .width(170.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column {
            shortDetails.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}