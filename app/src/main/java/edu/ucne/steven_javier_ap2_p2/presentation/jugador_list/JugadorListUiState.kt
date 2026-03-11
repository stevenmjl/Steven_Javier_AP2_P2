package edu.ucne.steven_javier_ap2_p2.presentation.jugador_list

import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador

data class JugadorListUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val error: String? = null
)