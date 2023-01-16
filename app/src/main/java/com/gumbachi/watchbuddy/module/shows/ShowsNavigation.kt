package com.gumbachi.watchbuddy.module.shows

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchbuddyMainDestination.Shows

fun NavGraphBuilder.showsScreen(
    navigateToDetails: (id: WatchBuddyID) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(destination.route) {
        ShowsScreen(
            viewModel = koinViewModel(),
            navigateToDetails = { movie ->
                navigateToDetails(movie.watchbuddyID)
            },
            modifier = modifier
        )
    }
}

fun NavController.navigateToShows() {
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