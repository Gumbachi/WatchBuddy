package com.gumbachi.watchbuddy.ui.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage

@Composable
fun PosterImage(
    posterURL: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    aspectRatio: Float = (2.0F/3.0F),
    matchConstraintByHeight: Boolean = false
) {
    SubcomposeAsyncImage(
        model = posterURL,
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