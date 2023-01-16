package com.gumbachi.watchbuddy.ui.toolbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaAppBar(
    title: String,
    onSortClicked: () -> Unit,
    onFilterClicked: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onSortClicked) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort"
                )
            }
            IconButton(onClick = onFilterClicked) {
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "Filter"
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
            MediaAppBar(title = "Movies", onSortClicked = { /*TODO*/ }, onFilterClicked = { /*TODO*/ })
        }
    }
}

@Preview
@Composable
private fun ActionBarPreviewDark() {
    ActionBarPreview(darkMode = false)
}