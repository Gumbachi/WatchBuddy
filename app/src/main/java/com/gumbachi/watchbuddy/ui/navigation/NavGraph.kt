package com.gumbachi.watchbuddy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gumbachi.watchbuddy.module.details.detailsScreen
import com.gumbachi.watchbuddy.module.details.navigateToDetails
import com.gumbachi.watchbuddy.module.movies.moviesScreen
import com.gumbachi.watchbuddy.module.search.home.navigateToSearchHome
import com.gumbachi.watchbuddy.module.search.home.searchHomeScreen
import com.gumbachi.watchbuddy.module.search.mediasearch.mediaSearchScreen
import com.gumbachi.watchbuddy.module.search.mediasearch.navigateToMediaSearch
import com.gumbachi.watchbuddy.module.settings.settingsScreen
import com.gumbachi.watchbuddy.module.shows.showsScreen

private const val TAG = "NavGraph"

@Composable
fun WatchBuddyNavGraph(
    startDestination: WatchbuddyMainDestination,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ) {

        // Main Destinations
        moviesScreen(
            navigateToDetails = navController::navigateToDetails,
            modifier = modifier
        )

        showsScreen(
            navigateToDetails = navController::navigateToDetails,
            modifier = modifier
        )

        searchHomeScreen(
            navigateToMediaSearch = navController::navigateToMediaSearch,
            navigateToDetails = navController::navigateToDetails
        )

        settingsScreen()


        // Secondary Destinations
        detailsScreen(
            navigateBack = navController::popBackStack,
            navigateToDetails = navController::navigateToDetails
        )

        mediaSearchScreen(
            navigateToSearchHome = navController::navigateToSearchHome,
            navigateToDetails = navController::navigateToDetails,
            navigateToMedia = navController::navigateToMedia
        )
    }
}
