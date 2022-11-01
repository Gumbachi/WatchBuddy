package com.gumbachi.watchbuddy.ui.app.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchbuddyActionBar(
    modifier: Modifier = Modifier,
    title: String = "WatchBuddy",
    searchButtonCallback: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        },
        actions = {
            IconButton(onClick = { searchButtonCallback() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ActionBarPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            WatchbuddyActionBar()
        }
    }
}

@Preview
@Composable
fun ActionBarPreviewDark() {
    ActionBarPreview(darkMode = true)
}