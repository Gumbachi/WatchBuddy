package com.gumbachi.watchbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gumbachi.watchbuddy.model.enums.configuration.BottomBarStyle
import com.gumbachi.watchbuddy.ui.components.appbars.WatchbuddyNavigationBar
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyNavGraph
import com.gumbachi.watchbuddy.ui.navigation.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Watchbuddy() {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

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
                    currentNavBackStackEntry = currentBackStackEntry,
                    onClick = { navController.navigateTo(it) }
                )
            })
        }
    ) { paddingValues ->
        WatchbuddyNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            setBottomBarStyle = { bottomBarStyle = it },
        )
    }
}
