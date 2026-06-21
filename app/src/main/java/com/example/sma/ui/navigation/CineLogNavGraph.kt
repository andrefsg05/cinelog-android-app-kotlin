package com.example.sma.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sma.ui.screens.detail.DetailScreen
import com.example.sma.ui.screens.home.HomeScreen
import com.example.sma.ui.screens.search.SearchScreen
import com.example.sma.ui.screens.watchlist.WatchlistScreen
import com.example.sma.ui.screens.random.RandomScreen

/**
 * Grafo de navegação da aplicação.
 */
@Composable
fun CineLogNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Ecrã Home
        composable(route = Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        // Ecrã Pesquisa
        composable(route = Screen.Search.route) {
            SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        // Ecrã Watchlist
        composable(route = Screen.Watchlist.route) {
            WatchlistScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        // Ecrã Random
        composable(route = Screen.Random.route) {
            RandomScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        // Ecrã Detalhes com argumento movieId
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            DetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
