package com.gumbachi.watchbuddy.components.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchbuddyBackAppBar(
    modifier: Modifier = Modifier,
    title: String = "WatchBuddy",
    onBackClicked: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back",
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
            WatchbuddyBackAppBar()
        }
    }
}

@Preview
@Composable
private fun ActionBarPreviewDark() {
    ActionBarPreview(darkMode = false)
}