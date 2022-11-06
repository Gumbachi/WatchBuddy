package com.gumbachi.watchbuddy.ui.app

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.SourceAPI

class WatchbuddyNavActions(navController: NavHostController) {
    val navigateToMovies: () -> Unit = {
        navController.navigate(WatchbuddyDestinations.MOVIES) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToShows: () -> Unit = {
        navController.navigate(WatchbuddyDestinations.SHOWS) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToDiscover: () -> Unit = {
        navController.navigate(WatchbuddyDestinations.DISCOVER) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(WatchbuddyDestinations.SETTINGS) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSearch: () -> Unit = {
        navController.navigate(WatchbuddyDestinations.SEARCH) {
            launchSingleTop = true
        }
    }

    val navigateToDetails: (SourceAPI, MediaType, Int) -> Unit = { api, type, id ->
        val route = "details/$api/$type/$id"
        Log.d("Navigation", "Navigating to $route")
        navController.navigate("details/$api/$type/$id") {
            launchSingleTop = true
        }
    }

    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}