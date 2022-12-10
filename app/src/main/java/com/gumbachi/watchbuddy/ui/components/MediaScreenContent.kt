package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MediaScreenContent(
    loadingState: Boolean,
    errorState: Throwable?,
    modifier: Modifier = Modifier,
    dialogs: @Composable () -> Unit = {},
    tabRow: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {

    dialogs()

    // Loading Display
    if (loadingState) {
        return Box(
            modifier = modifier.fillMaxSize().background(Color.Green),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    // Error Display
    errorState?.let {
        return Box(
            modifier = modifier.fillMaxSize().background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = it.message ?: "Unknown Error Occurred",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    // Main Content
    Column(modifier = modifier.fillMaxSize()) {
        tabRow()
        content()
    }
}