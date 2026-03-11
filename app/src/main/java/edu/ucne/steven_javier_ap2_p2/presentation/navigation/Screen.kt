package edu.ucne.steven_javier_ap2_p2.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object JugadorList : Screen

    @Serializable
    data class Jugador(val jugadorId: Int? = null) : Screen
}