package com.gumbachi.watchbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gumbachi.watchbuddy.components.appbars.WatchbuddyNavigationBar
import com.gumbachi.watchbuddy.model.enums.configuration.BottomBarStyle
import com.gumbachi.watchbuddy.navigation.WatchbuddyNavActions
import com.gumbachi.watchbuddy.navigation.WatchbuddyNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Watchbuddy() {

    var navIndex by remember { mutableStateOf(0) }

    val navController = rememberNavController()

    val navActions = remember(navController) {
        WatchbuddyNavActions(navController)
    }

    var bottomBarStyle by remember { mutableStateOf(BottomBarStyle.Shown) }
    val systemUiController = rememberSystemUiController()

    Scaffold(
        bottomBar = when (bottomBarStyle) {
            BottomBarStyle.Hidden -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.background
                )
            })
            BottomBarStyle.Shown -> ({
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
                WatchbuddyNavigationBar(
                    selected = navIndex,
                    onNavItemClick = {
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
            setNavIndex = { navIndex = it },
            setBottomBarStyle = { bottomBarStyle = it },
        )
    }
}
