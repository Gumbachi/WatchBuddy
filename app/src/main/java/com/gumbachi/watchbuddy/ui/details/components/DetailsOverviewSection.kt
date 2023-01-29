package com.gumbachi.watchbuddy.ui.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun DetailsOverviewSection(
    overview: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = overview ?: "None Provided",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            DetailsOverviewSection(overview = "Overview")
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    WatchBuddyTheme(darkTheme = false) {
        Surface {
            DetailsOverviewSection(overview = "Overview")
        }
    }
}