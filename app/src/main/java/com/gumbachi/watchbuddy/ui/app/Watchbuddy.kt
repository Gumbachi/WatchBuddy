package com.gumbachi.watchbuddy.ui.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gumbachi.watchbuddy.ui.app.components.WatchbuddyActionBar
import com.gumbachi.watchbuddy.ui.app.components.WatchbuddyNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Watchbuddy() {

    var showNavigationBars by remember { mutableStateOf(true) }
    var navIndex by remember { mutableStateOf(0) }

    val navController = rememberNavController()

    val navActions = remember(navController) {
        WatchbuddyNavActions(navController)
    }


    val systemUiController = rememberSystemUiController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = when (showNavigationBars) {
            false -> ({})
            true -> ({
                WatchbuddyActionBar(
                    searchButtonCallback = { navActions.navigateToSearch() },
                    scrollBehavior = scrollBehavior
                )
            })
        },
        bottomBar = when (showNavigationBars) {
            false -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.background
                )
            })
            true -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
                WatchbuddyNavigationBar(
                    selected = navIndex,
                    onNavItemClick = {
                        navIndex = it
                        when (it) {
                            1 -> navActions.navigateToShows()
                            2 -> navActions.navigateToDiscover()
                            3 -> navActions.navigateToSettings()
                            else -> navActions.navigateToMovies()
                        }
                    }
                )
            })
        }
    ) { paddingValues ->
        WatchbuddyNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            displayNavigationBars = { showNavigationBars = it }
        )
    }
}
