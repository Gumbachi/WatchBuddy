package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gumbachi.watchbuddy.ui.components.FixedText
import com.gumbachi.watchbuddy.utils.surfaceColorAtElevation

@Composable
fun DetailsPersonCard(
    imageURL: String,
    contentDescription: String,
    primaryDetail: String,
    secondaryDetail: String,
    modifier: Modifier = Modifier,
    primaryDetailLines: Int = 1,
    secondaryDetailLines: Int = 2
) {
    Card(
        modifier = modifier.size(150.dp, 260.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3)
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .crossfade(true)
                .build(),
            placeholder = rememberVectorPainter(image = Icons.Default.Downloading),
            error = rememberVectorPainter(image = Icons.Default.BrokenImage),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(150.dp, 200.dp)
        )
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
            FixedText(
                text = primaryDetail,
                style = MaterialTheme.typography.titleSmall,
                maxLines = primaryDetailLines,
                minLines = primaryDetailLines
            )
            FixedText(
                text = secondaryDetail,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                maxLines = secondaryDetailLines,
                minLines = secondaryDetailLines
            )
        }
    }
}