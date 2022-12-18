package com.gumbachi.watchbuddy.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gumbachi.watchbuddy.model.enums.configuration.BottomBarStyle
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import com.gumbachi.watchbuddy.module.details.DetailsScreen
import com.gumbachi.watchbuddy.module.movies.MoviesScreen
import com.gumbachi.watchbuddy.module.search.SearchScreen
import com.gumbachi.watchbuddy.module.settings.SettingsScreen
import com.gumbachi.watchbuddy.module.shows.ShowsScreen
import org.koin.androidx.compose.koinViewModel

private const val TAG = "NavGraph"

@Composable
fun WatchbuddyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    setBottomBarStyle: (BottomBarStyle) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = WatchbuddyDestination.MOVIES.route,
    ) {

        composable(
            route = WatchbuddyDestination.MOVIES.route,
            arguments = listOf(navArgument("id") {
                defaultValue = ""
                type = NavType.StringType
            })
        ) {
            setBottomBarStyle(WatchbuddyDestination.MOVIES.bottomBarStyle)

            MoviesScreen(
                viewModel = koinViewModel(),
                navigateToSearch = navController::navigateToSearch,
                navigateToDetails = { movie ->
                    navController.navigateToDetails(movie.watchbuddyID)
                },
                focusedItemId = it.arguments?.getString("id")?.let { idString ->
                    if (idString == "") null else idString.toWatchbuddyID()
                }
            )
        }


        // Shows Screen
        composable(route = WatchbuddyDestination.SHOWS.route) {
            setBottomBarStyle(WatchbuddyDestination.SHOWS.bottomBarStyle)
            ShowsScreen(
                modifier = modifier,
                viewModel = koinViewModel(),
                navigateToSearch = navController::navigateToSearch,
                navigateToDetails = { show ->
                    navController.navigateToDetails(show.watchbuddyID)
                }
            )
        }

        // Discover Screen
        composable(route = WatchbuddyDestination.DISCOVER.route) {
            setBottomBarStyle(WatchbuddyDestination.DISCOVER.bottomBarStyle)
            Text(text = "Discover Screen")
        }

        // Settings Screen
        composable(route = WatchbuddyDestination.SETTINGS.route) {
            setBottomBarStyle(WatchbuddyDestination.SETTINGS.bottomBarStyle)
            SettingsScreen(
                modifier = modifier,
                viewModel = koinViewModel()
            )
        }

        // Search Screen
        composable(route = WatchbuddyDestination.SEARCH.route) {
            setBottomBarStyle(WatchbuddyDestination.SEARCH.bottomBarStyle)
            SearchScreen(
                modifier = modifier,
                viewModel = koinViewModel(),
                onBackClick = { navController.popBackStack() },
                navigateToDetails = { navController.navigateToDetails(it.watchbuddyID) },
                navigateToWatchlist = {
                    navController.navigateToMedia(it)
                    Log.d("Howdy", "Navigating to watchlist for $it")
                }
            )
        }

        // Details Screen
        composable(
            route = WatchbuddyDestination.DETAILS.route,
            arguments = listOf(navArgument("wbid") { type = NavType.StringType })
        ) {
            setBottomBarStyle(WatchbuddyDestination.DETAILS.bottomBarStyle)
            it.arguments?.getString("wbid")?.let { idString ->
                DetailsScreen(
                    watchbuddyID = idString.toWatchbuddyID(),
                    viewModel = koinViewModel()
                )
            } ?: run {
                Text(
                    text = "NavArguments not available for some reason",
                    modifier = modifier
                )
            }
        }
    }
}
