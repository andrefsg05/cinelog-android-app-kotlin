package com.example.sma.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sma.data.model.Movie
import com.example.sma.data.repository.IMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado da UI do ecrã de detalhes.
 */
sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(
        val movie: Movie,
        val isInWatchlist: Boolean,
        val userRating: Float?
    ) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

/**
 * ViewModel para o ecrã de detalhes.
 */
class DetailViewModel(private val repository: IMovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    // Guarda o ID do filme carregado para evitar recarregamentos desnecessários.
    private var currentMovieId: Long? = null

    /**
     * Carrega os detalhes do filme.
     * Inclui verificação para não recarregar se o mesmo filme já está carregado.
     */
    fun loadMovie(movieId: Long) {
        // Evitar recarregamento se já temos dados para este filme.
        if (movieId == currentMovieId && _uiState.value !is DetailUiState.Error) return
        currentMovieId = movieId

        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val movie = repository.getMovieDetail(movieId)
                _uiState.value = DetailUiState.Success(
                    movie = movie,
                    isInWatchlist = repository.isInWatchlist(movieId),
                    userRating = repository.getUserRating(movieId)
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(
                    message = e.message ?: "Erro ao carregar detalhes"
                )
            }
        }
    }

    fun toggleWatchlist(movie: Movie) {
        val isAdded = repository.toggleWatchlist(movie)

        val currentState = _uiState.value
        if (currentState is DetailUiState.Success) {
            _uiState.value = currentState.copy(isInWatchlist = isAdded)
        }
    }

    fun setRating(movieId: Long, rating: Float) {
        repository.setUserRating(movieId, rating)

        val currentState = _uiState.value
        if (currentState is DetailUiState.Success) {
            _uiState.value = currentState.copy(userRating = rating)
        }
    }
}
