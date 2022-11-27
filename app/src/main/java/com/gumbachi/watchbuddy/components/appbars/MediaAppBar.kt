package com.gumbachi.watchbuddy.components.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchbuddyMediaAppBar(
    modifier: Modifier = Modifier,
    title: String = "WatchBuddy",
    onSearchClicked: () -> Unit = {},
    onFilterClicked: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                )
            }
        },
        actions = {
            IconButton(onClick = onFilterClicked) {
                Icon(
                    imageVector = Icons.Filled.Sort,
                    contentDescription = "Sort"
                )
            }
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
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
private fun ActionBarPreview(darkMode: Boolean = true) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            WatchbuddyMediaAppBar()
        }
    }
}

@Preview
@Composable
private fun ActionBarPreviewDark() {
    ActionBarPreview(darkMode = false)
}