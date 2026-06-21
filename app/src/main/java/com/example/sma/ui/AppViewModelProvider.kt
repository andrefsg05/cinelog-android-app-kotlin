package com.example.sma.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sma.CineLogApplication
import com.example.sma.ui.screens.detail.DetailViewModel
import com.example.sma.ui.screens.home.HomeViewModel
import com.example.sma.ui.screens.random.RandomViewModel
import com.example.sma.ui.screens.search.SearchViewModel
import com.example.sma.ui.screens.watchlist.WatchlistViewModel

/**
 * Provider factory universal para injetar o repositório nos ViewModels.
 * Cria ViewModels com acesso ao repositório da aplicação.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(cineLogApplication().container.movieRepository)
        }
        initializer {
            SearchViewModel(cineLogApplication().container.movieRepository)
        }
        initializer {
            DetailViewModel(cineLogApplication().container.movieRepository)
        }
        initializer {
            WatchlistViewModel(cineLogApplication().container.movieRepository)
        }
        initializer {
            RandomViewModel(cineLogApplication().container.movieRepository)
        }
    }
}

/**
 * Ajuda a aceder à aplicação a partir dos CreationExtras.
 */
fun CreationExtras.cineLogApplication(): CineLogApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CineLogApplication)
