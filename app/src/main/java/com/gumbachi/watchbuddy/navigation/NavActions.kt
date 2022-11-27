package com.gumbachi.watchbuddy.navigation

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.gumbachi.watchbuddy.model.enums.configuration.BottomBarStyle
import com.gumbachi.watchbuddy.model.enums.data.Source

sealed class WatchBuddyDestinations(
    val route: String,
    val bottomBarStyle: BottomBarStyle
) {
    object MOVIES: WatchBuddyDestinations(
        route = "movies",
        bottomBarStyle = BottomBarStyle.Shown
    )
    object SHOWS: WatchBuddyDestinations(
        route = "shows",
        bottomBarStyle = BottomBarStyle.Shown
    )
    object DISCOVER: WatchBuddyDestinations(
        route = "discover",
        bottomBarStyle = BottomBarStyle.Shown
    )
    object SETTINGS: WatchBuddyDestinations(
        route = "settings",
        bottomBarStyle = BottomBarStyle.Shown
    )
    object SEARCH: WatchBuddyDestinations(
        route = "search",
        bottomBarStyle = BottomBarStyle.Hidden
    )
    object DETAILS: WatchBuddyDestinations(
        route = "details/{api}/{type}/{id}",
        bottomBarStyle = BottomBarStyle.Hidden
    )
}


class WatchbuddyNavActions(navController: NavHostController) {
    val navigateToMovies: () -> Unit = {
        Log.d("Navigation", "Navigating to Movies")
        navController.navigate(WatchBuddyDestinations.MOVIES.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToShows: () -> Unit = {
        Log.d("Navigation", "Navigating to Shows")
        navController.navigate(WatchBuddyDestinations.SHOWS.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToDiscover: () -> Unit = {
        Log.d("Navigation", "Navigating to Discover")
        navController.navigate(WatchBuddyDestinations.DISCOVER.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        Log.d("Navigation", "Navigating to Settings")
        navController.navigate(WatchBuddyDestinations.SETTINGS.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSearch: () -> Unit = {
        Log.d("Navigation", "Navigating to Search")
        navController.navigate(WatchBuddyDestinations.SEARCH.route) {
            launchSingleTop = true
        }
    }

    val navigateToDetails: (Source, Int) -> Unit = { source, id ->
        val route = "details/${source.toDetailsRoute(id)}"
        Log.d("Navigation", "Navigating to $route")
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    val navigateBack: () -> Unit = {
        Log.d("Navigation", "Navigating Back")
        navController.popBackStack()
    }
}