package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun LoadingDisplay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            LoadingDisplay()
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(dark = false)
}