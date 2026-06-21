package com.example.sma.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representa a resposta de listas da API do TMDB.
 */
@Serializable
data class MovieResponse(
    @SerialName("page") val page: Int = 1,
    @SerialName("results") val results: List<Movie> = emptyList(),
    @SerialName("total_pages") val totalPages: Int = 0,
    @SerialName("total_results") val totalResults: Int = 0
)

/**
 * Representa um género da API do TMDB.
 */
@Serializable
data class Genre(
    val id: Int = 0,
    val name: String = ""
)

/**
 * Representa um filme da API do TMDB (usado para lista e detalhes).
 */
@Serializable
data class Movie(
    @SerialName("id") val id: Long,
    @SerialName("title") val apiTitle: String? = null,
    @SerialName("overview") val apiOverview: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int>? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("genres") val genres: List<Genre>? = null,
    @SerialName("tagline") val tagline: String? = null
) {
    val title: String
        get() = apiTitle ?: "Desconhecido"

    val overview: String
        get() = apiOverview ?: "Sinopse não disponível."

    val posterUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }

    val backdropUrl: String?
        get() = backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" }

    val releaseYear: String
        get() = releaseDate?.takeIf { it.length >= 4 }?.take(4) ?: "N/A"

    val formattedRating: String
        get() = voteAverage?.let { String.format("%.1f", it) } ?: "N/A"

    val formattedRuntime: String
        get() = runtime?.let {
            val hours = it / 60
            val mins = it % 60
            if (hours > 0) "${hours}h ${mins}min" else "${mins}min"
        } ?: "N/A"

    val genreNames: String
        get() = genres?.joinToString(", ") { it.name } ?: "N/A"
}


/**
 * Enum class para categorias de filmes
 */
enum class MovieCategory {
    NOW_PLAYING,
    POPULAR,
    TOP_RATED
}

/**
 * Filtra filmes com poster.
 */
fun List<Movie>.withPoster(): List<Movie> = filter { it.posterUrl != null }

/**
 * Ordena filmes por avaliação.
 */
fun List<Movie>.sortedByRating(): List<Movie> = sortedByDescending { it.voteAverage ?: 0.0 }
