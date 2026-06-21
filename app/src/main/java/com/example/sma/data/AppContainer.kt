package com.example.sma.data

import com.example.sma.data.network.TmdbApiService
import com.example.sma.data.repository.IMovieRepository
import com.example.sma.data.repository.MovieRepository

/**
 * Recipiente de dependências para a aplicação.
 */
interface AppContainer {
    val movieRepository: IMovieRepository
}

/**
 * Implementação do recipiente que inicializa o repositório e o serviço da API.
 */
class DefaultAppContainer : AppContainer {
    private val apiService: TmdbApiService by lazy {
        TmdbApiService.create()
    }

    override val movieRepository: IMovieRepository by lazy {
        MovieRepository(apiService)
    }
}
