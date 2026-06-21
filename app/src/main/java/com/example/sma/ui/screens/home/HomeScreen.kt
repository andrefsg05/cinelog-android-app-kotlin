package com.example.sma.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sma.R
import com.example.sma.data.model.Movie
import com.example.sma.ui.components.MovieCard

/**
 * Ecrã principal da aplicação.
 */
@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = com.example.sma.ui.AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Renderização condicional baseada no estado.
    when (val state = uiState) {
        is HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Error -> ErrorScreen(
            message = state.message,
            onRetry = { viewModel.loadMovies() }
        )
        is HomeUiState.Success -> HomeContent(
            popularMovies = state.popularMovies,
            nowPlayingMovies = state.nowPlayingMovies,
            topRatedMovies = state.topRatedMovies,
            onMovieClick = onMovieClick
        )
    }
}

/**
 * Conteúdo principal do Home com secções de filmes.
 */
@Composable
private fun HomeContent(
    popularMovies: List<Movie>,
    nowPlayingMovies: List<Movie>,
    topRatedMovies: List<Movie>,
    onMovieClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // Título da app
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Secção: Em Cartaz
        MovieSection(
            title = stringResource(R.string.now_playing),
            movies = nowPlayingMovies,
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Secção: Populares
        MovieSection(
            title = stringResource(R.string.popular),
            movies = popularMovies,
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Secção: Mais Bem Avaliados
        MovieSection(
            title = stringResource(R.string.top_rated),
            movies = topRatedMovies,
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * Secção reutilizável com título e lista horizontal de filmes.
 */
@Composable
private fun MovieSection(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Long) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = movies,
                key = { movie -> movie.id }
            ) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
    }
}

/**
 * Ecrã de carregamento.
 */
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Ecrã de erro com botão de retry.
 */
@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.error_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}
