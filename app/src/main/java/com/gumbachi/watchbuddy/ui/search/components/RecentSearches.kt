package com.gumbachi.watchbuddy.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import java.time.LocalDateTime

@Composable
fun RecentSearchesList(
    searches: List<RecentSearch>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(searches) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = "Recent Search",
                    modifier = Modifier.padding(16.dp)
                )
                Text(text = it.query)
                Text(text = "${it.resultCount} results")
                Text(text = "TODOm ago")
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Remove from history",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            RecentSearchesList(
                searches = listOf(
                    RecentSearch("howdy", 23, LocalDateTime.now()),
                    RecentSearch("howdy2", 102, LocalDateTime.now())
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    Preview(false)
}