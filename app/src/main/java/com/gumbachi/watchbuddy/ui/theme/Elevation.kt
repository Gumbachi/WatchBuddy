package com.gumbachi.watchbuddy.ui.theme

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