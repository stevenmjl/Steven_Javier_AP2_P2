package edu.ucne.steven_javier_ap2_p2.presentation.jugador_entry

data class JugadorEntryUiState(
    val jugadorId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val nombresError: String? = null,
    val emailError: String? = null
)