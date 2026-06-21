package com.example.sma.ui.screens.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sma.data.model.Movie
import com.example.sma.data.repository.IMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado da UI do ecrã Random.
 */
sealed interface RandomUiState {
    object Loading : RandomUiState
    data class Success(val movies: List<Movie>) : RandomUiState
    data class Error(val message: String) : RandomUiState
}

/**
 * ViewModel para o ecrã Random.
 */
class RandomViewModel(private val repository: IMovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RandomUiState>(RandomUiState.Loading)
    val uiState: StateFlow<RandomUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    /**
     * Carrega filmes populares para ter uma pool de seleção aleatória.
     */
    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = RandomUiState.Loading
            try {
                val popular = repository.getPopularMovies()
                _uiState.value = RandomUiState.Success(popular)
            } catch (e: Exception) {
                _uiState.value = RandomUiState.Error(
                    message = e.message ?: "Erro desconhecido ao carregar filmes"
                )
            }
        }
    }
}
