package com.gumbachi.watchbuddy.ui.components.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaAppBar(
    modifier: Modifier = Modifier,
    title: String = "WatchBuddy",
    onSortClicked: () -> Unit = {},
    onFilterClicked: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    sortIcon: ImageVector = Icons.Filled.Sort,
    filterIcon: ImageVector = Icons.Filled.FilterList
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onSortClicked) {
                Icon(
                    imageVector = sortIcon,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = onFilterClicked) {
                Icon(
                    imageVector = filterIcon,
                    contentDescription = "Sort"
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
            MediaAppBar()
        }
    }
}

@Preview
@Composable
private fun ActionBarPreviewDark() {
    ActionBarPreview(darkMode = false)
}