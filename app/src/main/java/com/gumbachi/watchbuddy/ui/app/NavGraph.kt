package com.gumbachi.watchbuddy.ui.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

object WatchbuddyDestinations {
    const val MOVIES = "movies"
    const val SHOWS = "shows"
    const val DISCOVER = "discover"
    const val SETTINGS = "settings"
    const val DETAILS = "details{id}"
    const val SEARCH = "search"
}

@Composable
fun WatchbuddyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    displayNavigationBars: (Boolean) -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = WatchbuddyDestinations.MOVIES,
    ) {

        // Movies Screen
        composable(route = WatchbuddyDestinations.MOVIES) {
//            MoviesScreen(
//                viewModel = moviesScreenViewModel,
//                modifier = modifier
//            )
        }

        // Shows Screen
        composable(route = WatchbuddyDestinations.SHOWS) {
//            ShowsScreen(modifier = modifier)
        }

        // Discover Screen
        composable(route = WatchbuddyDestinations.DISCOVER) {
            Text(text = "Discover Screen")
        }

        // Settings Screen
        composable(route = WatchbuddyDestinations.SETTINGS) {
//            SettingsScreen(modifier = modifier)
        }

        composable(
            route = WatchbuddyDestinations.DETAILS,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            Text(text = "Details Screen: ${it.arguments?.getInt("id", -1)}")
        }

        composable(route = WatchbuddyDestinations.SEARCH) {
            displayNavigationBars(false)
//            SearchScreen(
//                modifier = modifier,
//                backCallback = {
//                    navBarState = true
//                    navController.popBackStack()
//                }
//            )
        }
    }
}
