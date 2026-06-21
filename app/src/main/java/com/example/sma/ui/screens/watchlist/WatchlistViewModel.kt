package com.example.sma.ui.screens.watchlist

import androidx.lifecycle.ViewModel
import com.example.sma.data.model.Movie
import com.example.sma.data.repository.IMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para o ecrã da Watchlist.
 */
class WatchlistViewModel(private val repository: IMovieRepository) : ViewModel() {


    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    init {
        refreshWatchlist()
    }

    /**
     * Atualizar a lista da watchlist.
     */
    fun refreshWatchlist() {
        _movies.value = repository.watchlist
    }

    /**
     * Remover filme da watchlist.
     */
    fun removeFromWatchlist(movie: Movie) {
        repository.toggleWatchlist(movie)
        refreshWatchlist()
    }

    /**
     * Obter rating do utilizador para um filme.
     */
    fun getUserRating(movieId: Long): Float? {
        return repository.getUserRating(movieId)
    }
}
