package com.gumbachi.watchbuddy.module.search.home

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchbuddyMainDestination.SearchHome

fun NavGraphBuilder.searchHomeScreen(
    navigateToMediaSearch: () -> Unit
) {
    composable(destination.route) {
        SearchHomeScreen(
            viewModel = koinViewModel(),
            navigateToMediaSearch = navigateToMediaSearch
        )
    }
}

fun NavController.navigateToSearchHome() {
    if (currentDestination?.route == destination.route) return

    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}