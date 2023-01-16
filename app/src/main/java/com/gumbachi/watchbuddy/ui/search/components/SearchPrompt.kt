package com.gumbachi.watchbuddy.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun SearchPrompt(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "Search for Media",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun NothingFoundPrompt(
    query: String,
    modifier: Modifier = Modifier,
    onCreateCustomClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.SearchOff,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "After searching far and wide, nothing was found for",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = query,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Button(onClick = onCreateCustomClick) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add custom media")
            Text(text = "Add Custom")
        }
    }
}

@Preview
@Composable
private fun PreviewSearchPrompt(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            SearchPrompt()
        }
    }
}

@Preview
@Composable
private fun PreviewSearchPromptLight() {
    PreviewSearchPrompt(false)
}

@Preview
@Composable
private fun PreviewNothingFoundPrompt(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            NothingFoundPrompt("Howdy Howdy Howdy Triple Deluxe")
        }
    }
}

@Preview
@Composable
private fun PreviewNothingFoundPromptLight() {
     PreviewNothingFoundPrompt(false)
}