package com.gumbachi.watchbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gumbachi.watchbuddy.ui.components.appbars.WatchbuddyNavigationBar
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyDestination
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyNavGraph
import com.gumbachi.watchbuddy.ui.navigation.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Watchbuddy() {

    val mainRoutes = listOf(
        WatchbuddyDestination.MOVIES.route,
        WatchbuddyDestination.SHOWS.route,
        WatchbuddyDestination.SEARCH.route,
        WatchbuddyDestination.DISCOVER.route,
        WatchbuddyDestination.SETTINGS.route,
    )

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf { currentBackStackEntry?.destination?.route in mainRoutes }
    }

    val systemUiController = rememberSystemUiController()

    Scaffold(
        bottomBar = when (showBottomBar) {
            true -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
                WatchbuddyNavigationBar(
                    currentNavBackStackEntry = currentBackStackEntry,
                    onClick = { navController.navigateTo(it) }
                )
            })

            false -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.background
                )
            })

        }
    ) { paddingValues ->
        WatchbuddyNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
