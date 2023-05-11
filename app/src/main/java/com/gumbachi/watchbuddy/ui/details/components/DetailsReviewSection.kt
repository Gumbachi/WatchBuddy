package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn

@Composable
fun <T> DetailsReviewSection(
    reviews: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    ColorWrappedColumn(innerPadding = PaddingValues(8.dp), modifier = modifier) {
        DetailsClickCarousel(title = "Reviews", items = reviews) {
            content(it)
        }
    }
}