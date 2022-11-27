package com.gumbachi.watchbuddy.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun getElevation(level: Int) = when (level) {
    0 -> 0.dp
    1 -> 1.dp
    2 -> 3.dp
    3 -> 6.dp
    4 -> 8.dp
    5 -> 12.dp
    else -> 0.dp
}

fun ColorScheme.surfaceColorAtElevationLevel(level: Int): Color {
    return this.surfaceColorAtElevation(getElevation(level))
}