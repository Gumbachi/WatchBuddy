package com.gumbachi.watchbuddy.module.movies

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchbuddyMainDestination.Movies

fun NavGraphBuilder.moviesScreen(
    navigateToDetails: (id: WatchBuddyID) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(destination.route) {
        MoviesScreen(
            viewModel = koinViewModel(),
            navigateToDetails = { movie ->
                navigateToDetails(movie.watchbuddyID)
            },
            focusedItemId = it.arguments?.getString("id")?.let { idString ->
                if (idString == "") null else idString.toWatchbuddyID()
            },
            modifier = modifier
        )
    }
}

fun NavController.navigateToMovies(mediaID: WatchBuddyID? = null) {
    if (currentDestination?.route == destination.route) return

    val route = destination.buildRoute(mediaID)

    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}