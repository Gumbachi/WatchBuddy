package com.gumbachi.watchbuddy.components.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun WatchbuddyNavigationBar(
    selected: Int,
    modifier: Modifier = Modifier,
    onNavItemClick: (Int) -> Unit = {},
) {

    val items = listOf(
        "Movies" to Icons.Filled.Movie,
        "Shows" to Icons.Filled.Tv,
        "Discover" to Icons.Filled.CellTower,
        "Settings" to Icons.Filled.Settings
    )

    NavigationBar(
        modifier = modifier,
        tonalElevation = 3.dp
    ) {
        items.forEachIndexed { index, item ->
            val (name, icon) = item
            NavigationBarItem(
                label = { Text(text = name) },
                icon = { Icon(icon, contentDescription = null) },
                selected = selected == index,
                onClick = { onNavItemClick(index) },
            )
        }
    }
}

@Preview
@Composable
fun NavigationBarPreview(darkMode: Boolean = false) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            WatchbuddyNavigationBar(selected = 1)
        }
    }
}

@Preview
@Composable
fun NavigationBarPreviewDark() {
    NavigationBarPreview(darkMode = true)
}