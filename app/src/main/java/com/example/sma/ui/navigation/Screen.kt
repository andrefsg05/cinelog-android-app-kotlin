package com.example.sma.ui.navigation

/**
 * Define as rotas de navegação.
 * Agrupa os destinos usados pela aplicação.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Watchlist : Screen("watchlist")
    object Random : Screen("random")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Long): String = "detail/$movieId"
    }
}
