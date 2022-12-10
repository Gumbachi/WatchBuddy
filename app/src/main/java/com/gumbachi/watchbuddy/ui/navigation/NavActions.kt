package com.gumbachi.watchbuddy.ui.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.configuration.BottomBarStyle
import com.gumbachi.watchbuddy.model.enums.data.MediaType

private const val TAG = "Navigation"

sealed class WatchbuddyDestination(
    val route: String,
    val name: String,
    val icon: ImageVector,
    val bottomBarStyle: BottomBarStyle
) {
    object MOVIES : WatchbuddyDestination(
        route = "movies?id={id}",
        name = "Movies",
        icon = Icons.Filled.Movie,
        bottomBarStyle = BottomBarStyle.Shown
    ) {
        fun buildRoute(startID: WatchbuddyID?) =
            if (startID == null) route else "movies?id=$startID"
    }

    object SHOWS : WatchbuddyDestination(
        route = "shows?id={id}",
        name = "Shows",
        icon = Icons.Filled.Tv,
        bottomBarStyle = BottomBarStyle.Shown
    ) {
        fun buildRoute(startID: WatchbuddyID?) =
            if (startID == null) route else "shows?id=$startID"
    }

    object DISCOVER : WatchbuddyDestination(
        route = "discover",
        name = "Discover",
        icon = Icons.Filled.CellTower,
        bottomBarStyle = BottomBarStyle.Shown
    )

    object SETTINGS : WatchbuddyDestination(
        route = "settings",
        name = "Settings",
        icon = Icons.Filled.Settings,
        bottomBarStyle = BottomBarStyle.Shown
    )

    object SEARCH : WatchbuddyDestination(
        route = "search",
        name = "Search",
        icon = Icons.Filled.Search,
        bottomBarStyle = BottomBarStyle.Hidden
    )

    object DETAILS : WatchbuddyDestination(
        route = "details/{wbid}",
        name = "Details",
        icon = Icons.Filled.Details,
        bottomBarStyle = BottomBarStyle.Hidden
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
    Log.d(TAG, "Navigating to Movies")
    navigate(WatchbuddyDestination.MOVIES.route) {
        popUpTo(0) {
            saveState = true
            inclusive = true
        }
        restoreState = true
    }
}

fun NavHostController.navigateToShows() {
    Log.d(TAG, "Navigating to Shows")

    navigate(WatchbuddyDestination.SHOWS.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
    }
}

fun NavHostController.navigateToDiscover() {
    Log.d(TAG, "Navigating to Discover")
    navigate(WatchbuddyDestination.DISCOVER.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
    }
}

fun NavHostController.navigateToSettings() {
    Log.d(TAG, "Navigating to SETTINGS")
    navigate(WatchbuddyDestination.SETTINGS.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        restoreState = true
    }
}

fun NavHostController.navigateToSearch() {
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
         }
     }
}
