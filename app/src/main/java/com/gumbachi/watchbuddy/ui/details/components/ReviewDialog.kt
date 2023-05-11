package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gumbachi.watchbuddy.model.interfaces.Review

@Composable
fun ReviewDialog(
    review: Review,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shown: Boolean = true
) {
    if (!shown) return

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {},
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = review.avatarURL,
                    placeholder = rememberVectorPainter(image = Icons.Filled.Person),
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
        },
        text = {
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.verticalScroll(rememberScrollState()),
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier.fillMaxHeight(0.8F)
    )
}
