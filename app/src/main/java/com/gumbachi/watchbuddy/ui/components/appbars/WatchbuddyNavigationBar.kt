package com.gumbachi.watchbuddy.ui.components.appbars

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyDestination

@Composable
fun WatchbuddyNavigationBar(
    currentNavBackStackEntry: NavBackStackEntry?,
    onClick: (WatchbuddyDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    val destinations = listOf(
        WatchbuddyDestination.DISCOVER,
        WatchbuddyDestination.MOVIES,
        WatchbuddyDestination.SEARCH,
        WatchbuddyDestination.SHOWS,
        WatchbuddyDestination.SETTINGS
    )

    val currentDestination = currentNavBackStackEntry?.destination

    NavigationBar(
        modifier = modifier,
        tonalElevation = 3.dp
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                label = { Text(text = destination.name) },
                icon = { Icon(
                    imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                    contentDescription = null
                ) },
                selected = selected,
                onClick = { onClick(destination) },
            )
        }
    }
}