package com.example.sma.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sma.data.model.Movie
import com.example.sma.data.repository.IMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GenreFilter(
    val name: String,
    val tmdbId: Int
)

/**
 * Estado da pesquisa.
 */
sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val movies: List<Movie>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

/**
 * ViewModel para o ecrã de pesquisa.
 */
class SearchViewModel(private val repository: IMovieRepository) : ViewModel() {

    companion object {
        val genreFilters = listOf(
            GenreFilter("Ação", 28),
            GenreFilter("Comédia", 35),
            GenreFilter("Drama", 18),
            GenreFilter("Ficção Científica", 878),
            GenreFilter("Terror", 27),
            GenreFilter("Romance", 10749),
            GenreFilter("Animação", 16)
        )
    }

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    /**
     * Atualiza a query de pesquisa.
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Idle
        }
    }

    /**
     * Executa a pesquisa manual.
     */
    fun executeSearch() {
        val query = _searchQuery.value
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val results = repository.searchMovies(query)
                _uiState.value = if (results.isEmpty()) {
                    SearchUiState.Success(emptyList())
                } else {
                    SearchUiState.Success(results)
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(
                    message = e.message ?: "Erro na pesquisa"
                )
            }
        }
    }

    /**
     * Pesquisa filmes por categoria/género.
     */
    fun searchByGenre(genreName: String, genreId: Int) {
        _searchQuery.value = genreName

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val results = repository.getMoviesByGenre(genreId)
                _uiState.value = if (results.isEmpty()) {
                    SearchUiState.Success(emptyList())
                } else {
                    SearchUiState.Success(results)
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(
                    message = e.message ?: "Erro na pesquisa por categoria"
                )
            }
        }
    }
}
