package com.gumbachi.watchbuddy.navigation

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
import com.gumbachi.watchbuddy.model.enums.configuration.BottomBarStyle
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.ui.screens.details.DetailsScreen
import com.gumbachi.watchbuddy.ui.screens.details.DetailsViewModel
import com.gumbachi.watchbuddy.ui.screens.movies.MoviesScreen
import com.gumbachi.watchbuddy.ui.screens.search.SearchScreen
import com.gumbachi.watchbuddy.ui.screens.settings.SettingsScreen

@Composable
fun WatchbuddyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    setNavIndex: (Int) -> Unit = {},
    setBottomBarStyle: (BottomBarStyle) -> Unit = {}
) {

    val navActions = remember(navController) {
        WatchbuddyNavActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = WatchBuddyDestinations.MOVIES.route,
    ) {

        composable(route = WatchBuddyDestinations.MOVIES.route) {
            setNavIndex(0)
            setBottomBarStyle(WatchBuddyDestinations.MOVIES.bottomBarStyle)
            MoviesScreen(
                modifier = modifier,
                viewModel = hiltViewModel(),
                navigateToSearch = navActions.navigateToSearch,
                navigateToDetails = {
                    navActions.navigateToDetails(it.source, it.id)
                }
            )
        }


        // Shows Screen
        composable(route = WatchBuddyDestinations.SHOWS.route) {
            setNavIndex(1)
            setBottomBarStyle(WatchBuddyDestinations.SHOWS.bottomBarStyle)
        }

        // Discover Screen
        composable(route = WatchBuddyDestinations.DISCOVER.route) {
            setNavIndex(2)
            setBottomBarStyle(WatchBuddyDestinations.DISCOVER.bottomBarStyle)
            Text(text = "Discover Screen")
        }

        // Settings Screen
        composable(route = WatchBuddyDestinations.SETTINGS.route) {
            setNavIndex(3)
            setBottomBarStyle(WatchBuddyDestinations.SETTINGS.bottomBarStyle)
            SettingsScreen(
                modifier = modifier,
                viewModel = hiltViewModel()
            )
        }

        // Search Screen
        composable(route = WatchBuddyDestinations.SEARCH.route) {
            setBottomBarStyle(WatchBuddyDestinations.SEARCH.bottomBarStyle)
            SearchScreen(
                modifier = modifier,
                viewModel = hiltViewModel(),
                onBackClick = navActions.navigateBack,
                navigateToDetails = { navActions.navigateToDetails(it.source, it.id) },
                navigateToWatchlist = { navActions.navigateToMovies() }
            )
        }

        // Details Screen
        composable(
            route = WatchBuddyDestinations.DETAILS.route,
            arguments = listOf(
                navArgument("api") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            setBottomBarStyle(WatchBuddyDestinations.DETAILS.bottomBarStyle)

            if (it.arguments == null) {
                Text(
                    "NavArguments not available for some reason",
                    modifier = modifier
                )
                return@composable
            }

            val id = it.arguments!!.getInt("id", -1)
            val type = it.arguments!!.getString("type")
            val api = it.arguments!!.getString("api")

            val source = Source.fromNavArgs(api, type)


            if (id == -1 || source == null) {
                Text(
                    "Invalid Source or ID",
                    modifier = modifier
                )
                return@composable
            }

            val viewModel: DetailsViewModel = hiltViewModel()
            DetailsScreen(
                source = source,
                id = id,
                viewModel = viewModel
            )
        }
    }
}
