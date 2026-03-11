package edu.ucne.steven_javier_ap2_p2.presentation.jugador_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadorListScreen(
    viewModel: JugadorListViewModel = hiltViewModel(),
    onAddJugador: () -> Unit,
    onJugadorClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Jugadores") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddJugador) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Jugador")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.jugadores) { jugador ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onJugadorClick(jugador.jugadorId) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = jugador.nombres, style = MaterialTheme.typography.titleMedium)
                            Text(text = jugador.email, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}