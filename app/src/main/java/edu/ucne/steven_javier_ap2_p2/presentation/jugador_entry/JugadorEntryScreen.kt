package edu.ucne.steven_javier_ap2_p2.presentation.jugador_entry

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadorScreen(
    viewModel: JugadorEntryViewModel = hiltViewModel(),
    jugadorId: Int? = null,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(jugadorId) {
        if (jugadorId != null && jugadorId > 0) {
            viewModel.loadJugador(jugadorId)
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (jugadorId == null) "Nuevo Jugador" else "Editar Jugador") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombres,
                onValueChange = { viewModel.onEvent(JugadorEntryEvent.OnNombresChange(it)) },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.nombresError != null,
                supportingText = { state.nombresError?.let { Text(it) } }
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(JugadorEntryEvent.OnEmailChange(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.emailError != null,
                supportingText = { state.emailError?.let { Text(it) } }
            )

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
            }

            state.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = { viewModel.onEvent(JugadorEntryEvent.Save) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Guardar Jugador")
            }
        }
    }
}