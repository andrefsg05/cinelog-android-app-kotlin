package com.example.sma.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sma.data.model.Movie
import com.example.sma.data.model.withPoster
import com.example.sma.data.model.sortedByRating
import com.example.sma.data.repository.IMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado da UI do ecrã Home.
 */
sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(
        val popularMovies: List<Movie>,
        val nowPlayingMovies: List<Movie>,
        val topRatedMovies: List<Movie>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

/**
 * ViewModel para o ecrã Home.
 */
class HomeViewModel(private val repository: IMovieRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    /**
     * Carrega filmes de múltiplas categorias sequencialmente.
     */
    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                // Filtra e ordena os filmes apresentados no Home.
                val popular = repository.getPopularMovies().withPoster()
                val nowPlaying = repository.getNowPlayingMovies().withPoster()
                val topRated = repository.getTopRatedMovies().withPoster().sortedByRating()

                _uiState.value = HomeUiState.Success(
                    popularMovies = popular,
                    nowPlayingMovies = nowPlaying,
                    topRatedMovies = topRated
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    message = e.message ?: "Erro desconhecido ao carregar filmes"
                )
            }
        }
    }
}
