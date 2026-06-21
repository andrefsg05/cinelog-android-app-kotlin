package com.example.sma.data.repository

import com.example.sma.data.model.Movie
import com.example.sma.data.network.TmdbApiService

/**
 * Interface do repositório de filmes.
 */
interface IMovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMoviesByGenre(genreId: Int): List<Movie>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetail(movieId: Long): Movie
    fun toggleWatchlist(movie: Movie): Boolean
    fun isInWatchlist(movieId: Long): Boolean
    fun setUserRating(movieId: Long, rating: Float)
    fun getUserRating(movieId: Long): Float?
    val watchlist: List<Movie>
    val userRatings: Map<Long, Float>
}

/**
 * Repositório que serve como fonte única de dados
 */
class MovieRepository(
    private val apiService: TmdbApiService
) : IMovieRepository {

    private val _watchlist = mutableListOf<Movie>()
    override val watchlist: List<Movie> get() = _watchlist.toList()

    private val _userRatings = mutableMapOf<Long, Float>()
    override val userRatings: Map<Long, Float> get() = _userRatings.toMap()

    override suspend fun getPopularMovies(): List<Movie> {
        return apiService.getPopularMovies().results
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return apiService.getNowPlayingMovies().results
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return apiService.getTopRatedMovies().results
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<Movie> {
        return apiService.discoverMoviesByGenre(genreId).results
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return apiService.searchMovies(query).results
    }

    override suspend fun getMovieDetail(movieId: Long): Movie {
        return apiService.getMovieDetail(movieId)
    }

    override fun toggleWatchlist(movie: Movie): Boolean {
        return if (_watchlist.any { it.id == movie.id }) {
            _watchlist.removeAll { it.id == movie.id }
            false
        } else {
            _watchlist.add(movie)
            true
        }
    }

    override fun isInWatchlist(movieId: Long): Boolean {
        return _watchlist.any { it.id == movieId }
    }

    override fun setUserRating(movieId: Long, rating: Float) {
        _userRatings[movieId] = rating
    }

    override fun getUserRating(movieId: Long): Float? {
        return _userRatings[movieId]
    }
}
