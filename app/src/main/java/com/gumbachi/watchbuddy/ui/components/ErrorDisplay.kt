package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorDisplay(
    error: Throwable,
    modifier: Modifier = Modifier
) {
    return Box(
        modifier = modifier.fillMaxSize().background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error.message ?: "Unknown Error Occurred",
            style = MaterialTheme.typography.titleMedium
        )
    }
}