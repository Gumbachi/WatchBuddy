package com.gumbachi.watchbuddy.ui.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.CellTower
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.MediaType

private const val TAG = "Navigation"

sealed class WatchbuddyDestination(
    val route: String,
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object MOVIES : WatchbuddyDestination(
        route = "movies?id={id}",
        name = "Movies",
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie,
    ) {
        fun buildRoute(startID: WatchbuddyID?) =
            if (startID == null) route else "movies?id=$startID"
    }

    object SHOWS : WatchbuddyDestination(
        route = "shows?id={id}",
        name = "Shows",
        selectedIcon = Icons.Filled.Tv,
        unselectedIcon = Icons.Outlined.Tv,
    ) {
        fun buildRoute(startID: WatchbuddyID?) =
            if (startID == null) route else "shows?id=$startID"
    }

    object DISCOVER : WatchbuddyDestination(
        route = "discover",
        name = "Discover",
        selectedIcon = Icons.Filled.CellTower,
        unselectedIcon = Icons.Outlined.CellTower,
    )

    object SETTINGS : WatchbuddyDestination(
        route = "settings",
        name = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    )

    object SEARCH : WatchbuddyDestination(
        route = "search",
        name = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
    )

    object DETAILS : WatchbuddyDestination(
        route = "details/{wbid}",
        name = "Details",
        selectedIcon = Icons.Filled.Details,
        unselectedIcon = Icons.Outlined.Search,
    ) {
        fun buildRoute(id: WatchbuddyID) = "details/$id"
    }

}

fun NavHostController.navigateTo(destination: WatchbuddyDestination) {
    when (destination) {
        WatchbuddyDestination.MOVIES -> navigateToMovies()
        WatchbuddyDestination.DISCOVER -> navigateToDiscover()
        WatchbuddyDestination.SEARCH -> navigateToSearch()
        WatchbuddyDestination.SETTINGS -> navigateToSettings()
        WatchbuddyDestination.SHOWS -> navigateToShows()
        else -> TODO("Do Something fancy with this or throw an error")
    }
}


fun NavHostController.navigateToMovies() {

    if (currentDestination?.route == WatchbuddyDestination.MOVIES.route) return

    Log.d(TAG, "Navigating to Movies")

    navigate(WatchbuddyDestination.MOVIES.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
        launchSingleTop = true
    }
}

fun NavHostController.navigateToShows() {
    if (currentDestination?.route == WatchbuddyDestination.SHOWS.route) return
    Log.d(TAG, "Navigating to Shows")

    navigate(WatchbuddyDestination.SHOWS.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
        launchSingleTop = true
    }
}

fun NavHostController.navigateToDiscover() {
    if (currentDestination?.route == WatchbuddyDestination.DISCOVER.route) return
    Log.d(TAG, "Navigating to Discover")
    navigate(WatchbuddyDestination.DISCOVER.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
        launchSingleTop = true
    }
}

fun NavHostController.navigateToSettings() {
    if (currentDestination?.route == WatchbuddyDestination.SETTINGS.route) return
    Log.d(TAG, "Navigating to SETTINGS")
    navigate(WatchbuddyDestination.SETTINGS.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
        launchSingleTop = true
    }
}

fun NavHostController.navigateToSearch() {
    if (currentDestination?.route == WatchbuddyDestination.SEARCH.route) return
    Log.d(TAG, "Navigating to Search")
    navigate(WatchbuddyDestination.SEARCH.route) {
        launchSingleTop = true
    }
}

fun NavHostController.navigateToDetails(id: WatchbuddyID) {
    WatchbuddyDestination.DETAILS.buildRoute(id = id).let { route ->
        Log.d(TAG, "Navigating to $route")
        navigate(route) {
            launchSingleTop = true
        }
    }
}

fun NavHostController.navigateToMedia(id: WatchbuddyID) {
    Log.d(TAG, "Navigating to Media $id")
     when (id.type) {
        MediaType.Movie -> WatchbuddyDestination.MOVIES.buildRoute(id)
        MediaType.Show -> WatchbuddyDestination.SHOWS.buildRoute(id)
    }.let { route ->
         navigate(route) {
             popUpTo(0) {
                 saveState = true
                 inclusive = true
             }
             restoreState = true
             launchSingleTop = true
         }
     }
}
