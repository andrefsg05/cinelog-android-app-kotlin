package com.example.sma.ui.screens.random

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sma.R
import com.example.sma.ui.components.MovieCard

/**
 * Ecrã de filme aleatório.
 */
@Composable
fun RandomScreen(
    onMovieClick: (Long) -> Unit,
    viewModel: RandomViewModel = viewModel(factory = com.example.sma.ui.AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Estado local para guardar o resultado do sorteio.
    var result by remember { mutableStateOf(1) }
    var hasRolled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Renderização condicional baseada no estado.
        when (uiState) {
            is RandomUiState.Loading -> {
                CircularProgressIndicator()
            }
            is RandomUiState.Error -> {
                Text(
                    text = (uiState as RandomUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.loadMovies() }) {
                    Text(stringResource(R.string.retry))
                }
            }
            is RandomUiState.Success -> {
                val movies = (uiState as RandomUiState.Success).movies
                val selectedMovie = if (hasRolled) {
                    movies.getOrNull(result - 1)
                } else {
                    null
                }

                Text(
                    text = stringResource(R.string.random_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.random_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (selectedMovie != null) {
                    MovieCard(
                        movie = selectedMovie,
                        onClick = { onMovieClick(selectedMovie.id) }
                    )
                } else {
                    // Estado inicial antes do primeiro lançamento.
                    Icon(
                        imageVector = Icons.Filled.Casino,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (movies.isNotEmpty()) {
                            result = (1..movies.size).random()
                            hasRolled = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Icon(Icons.Filled.Casino, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.random_button))
                }
            }
        }
    }
}
