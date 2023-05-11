package com.gumbachi.watchbuddy.ui.cards

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EfficientPosterImage(
    posterURL: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    aspectRatio: Float = (2.0F/3.0F),
    matchConstraintByHeight: Boolean = false
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterURL)
            .crossfade(true)
            .build(),
        placeholder = rememberVectorPainter(Icons.Default.Download),
        error = rememberVectorPainter(Icons.Default.BrokenImage),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        modifier = modifier.aspectRatio(aspectRatio, matchConstraintByHeight)
    )
}