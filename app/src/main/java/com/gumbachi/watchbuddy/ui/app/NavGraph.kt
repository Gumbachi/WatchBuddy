package com.gumbachi.watchbuddy.ui.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.ui.screens.details.DetailsScreen
import com.gumbachi.watchbuddy.ui.screens.details.DetailsViewModel
import com.gumbachi.watchbuddy.ui.screens.movies.MoviesScreen
import com.gumbachi.watchbuddy.ui.screens.movies.MoviesScreenViewModel
import com.gumbachi.watchbuddy.ui.screens.search.SearchScreen
import com.gumbachi.watchbuddy.ui.screens.search.SearchViewModel
import com.gumbachi.watchbuddy.ui.screens.settings.SettingsScreen
import com.gumbachi.watchbuddy.ui.screens.settings.SettingsViewModel

object WatchbuddyDestinations {
    const val MOVIES = "movies"
    const val SHOWS = "shows"
    const val DISCOVER = "discover"
    const val SETTINGS = "settings"
    const val DETAILS = "details/{api}/{type}/{id}"
    const val SEARCH = "search"
}

@Composable
fun WatchbuddyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    displayNavigationBars: (Boolean) -> Unit = {},
) {

    val navActions = remember(navController) {
        WatchbuddyNavActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = WatchbuddyDestinations.MOVIES,
    ) {
        // Movies Screen
        composable(route = WatchbuddyDestinations.MOVIES) {
            displayNavigationBars(true)
            val viewModel: MoviesScreenViewModel = hiltViewModel()
            MoviesScreen(
                modifier = modifier,
                viewModel = viewModel,
                navigateToMovieDetails = { sourceAPI, id ->
                    navActions.navigateToDetails(sourceAPI, MediaType.Movie, id)
                }
            )
        }

        // Shows Screen
        composable(route = WatchbuddyDestinations.SHOWS) {
            displayNavigationBars(true)
//            ShowsScreen(modifier = modifier)
        }

        // Discover Screen
        composable(route = WatchbuddyDestinations.DISCOVER) {
            displayNavigationBars(true)
            Text(text = "Discover Screen")
        }

        // Settings Screen
        composable(route = WatchbuddyDestinations.SETTINGS) {
            displayNavigationBars(true)
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }

        composable(
            route = WatchbuddyDestinations.DETAILS,
            arguments = listOf(
                navArgument("api") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            displayNavigationBars(false)

            println("Loading details screen in navgraph")

            val id = it.arguments?.getInt("id", -1)
            val type = it.arguments?.getString("type")
            val api = it.arguments?.getString("api")


            if (id == null || id == -1 || api == null || type == null) {
                Text(
                    "Invalid Parameters",
                    modifier = modifier
                )
            } else {
                val viewModel: DetailsViewModel = hiltViewModel()
                DetailsScreen(
                    api = SourceAPI.fromString(api),
                    mediaType = MediaType.fromString(type),
                    id = id,
                    viewModel = viewModel
                )
            }
        }

        composable(route = WatchbuddyDestinations.SEARCH) {
            displayNavigationBars(false)
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                modifier = modifier,
                viewModel = viewModel,
                onBackClicked = navActions.navigateBack,
                navigateToDetails = { navActions.navigateToDetails(it.api, it.type, it.id) }
            )
        }
    }
}
