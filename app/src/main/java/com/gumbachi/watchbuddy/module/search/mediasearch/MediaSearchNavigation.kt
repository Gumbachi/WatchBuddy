package com.gumbachi.watchbuddy.module.search.mediasearch

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.ui.navigation.WatchBuddySecondaryDestination
import org.koin.androidx.compose.koinViewModel

private val destination = WatchBuddySecondaryDestination.MediaSearch

fun NavGraphBuilder.mediaSearchScreen(
    navigateToSearchHome: () -> Unit,
    navigateToDetails: (id: WatchBuddyID) -> Unit,
    navigateToMedia: (Media) -> Unit
) {
    composable(destination.route) {
        MediaSearchScreen(
            viewModel = koinViewModel(),
            navigateToSearchHome = navigateToSearchHome,
            navigateToDetails = navigateToDetails,
            navigateToMedia = navigateToMedia
        )
    }
}

fun NavController.navigateToMediaSearch() {
    if (currentDestination?.route == destination.route) return

    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id)
        launchSingleTop = true
    }
}