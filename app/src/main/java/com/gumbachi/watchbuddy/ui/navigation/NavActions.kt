package com.gumbachi.watchbuddy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.module.movies.navigateToMovies
import com.gumbachi.watchbuddy.module.search.home.navigateToSearchHome
import com.gumbachi.watchbuddy.module.settings.navigateToSettings
import com.gumbachi.watchbuddy.module.shows.navigateToShows

private const val TAG = "Navigation"

interface WatchBuddyDestination {
    val route: String
}

sealed class WatchbuddyMainDestination(
    override val route: String,
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
): WatchBuddyDestination {
    object Movies : WatchbuddyMainDestination(
        route = "movies?id={id}",
        name = "Movies",
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie,
    ) {
        fun buildRoute(startID: WatchBuddyID?) =
            if (startID == null) route else "movies?id=$startID"

        override fun toString() = name
    }

    object Shows : WatchbuddyMainDestination(
        route = "shows?id={id}",
        name = "Shows",
        selectedIcon = Icons.Filled.Tv,
        unselectedIcon = Icons.Outlined.Tv,
    ) {
        fun buildRoute(startID: WatchBuddyID?) =
            if (startID == null) route else "shows?id=$startID"

        override fun toString() = name
    }

    object Discover : WatchbuddyMainDestination(
        route = "discover",
        name = "Discover",
        selectedIcon = Icons.Filled.CellTower,
        unselectedIcon = Icons.Outlined.CellTower,
    ) {
        override fun toString() = name
    }

    object Settings : WatchbuddyMainDestination(
        route = "settings",
        name = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    ) {
        override fun toString() = name
    }

    object SearchHome : WatchbuddyMainDestination(
        route = "search/home",
        name = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
    ) {
        override fun toString() = name
    }

    companion object {
        fun values() = listOf(
            Movies,
            Shows,
            SearchHome,
            Settings
        )

        fun routes() = values().map { it.route }
        fun valueOf(name: String) = values().find { it.name == name }!!
    }
}

sealed class WatchBuddySecondaryDestination(
    override val route: String,
): WatchBuddyDestination {

    object MediaSearch : WatchBuddySecondaryDestination(
        route = "search/media",
    )

    object Details : WatchBuddySecondaryDestination(
        route = "details/{id}",
    ) {
        fun buildRoute(id: WatchBuddyID) = "details/$id"
    }
}

fun NavController.navigateToMedia(media: Media) {
    when (media) {
        is Movie -> navigateToMovies(media.watchbuddyID)
        is Show -> navigateToShows() // TODO
    }
}

fun NavController.navigate(destination: WatchbuddyMainDestination) {
    when (destination) {
        is WatchbuddyMainDestination.Movies -> navigateToMovies()
        is WatchbuddyMainDestination.Shows -> navigateToShows()
        is WatchbuddyMainDestination.SearchHome -> navigateToSearchHome()
        is WatchbuddyMainDestination.Settings -> navigateToSettings()
        else -> TODO("Discover Page NYI")
    }
}