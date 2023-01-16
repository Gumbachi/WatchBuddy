package com.gumbachi.watchbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gumbachi.watchbuddy.ui.navigation.WatchBuddyNavGraph
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import com.gumbachi.watchbuddy.ui.navigation.navigate
import com.gumbachi.watchbuddy.ui.toolbars.ComposeWatchBuddyNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Watchbuddy(
    startDestination: WatchbuddyMainDestination
) {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf {
            val currentRoute = currentBackStackEntry?.destination?.route
            currentRoute in WatchbuddyMainDestination.routes()
        }
    }

    val systemUiController = rememberSystemUiController()

    Scaffold(
        bottomBar = when (showBottomBar) {
            true -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
                ComposeWatchBuddyNavigationBar(
                    currentNavBackStackEntry = currentBackStackEntry,
                    onClick = { navController.navigate(it) }
                )
            })

            false -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.background
                )
            })

        }
    ) { paddingValues ->
        println(paddingValues)

        WatchBuddyNavGraph(
            startDestination = startDestination,
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
