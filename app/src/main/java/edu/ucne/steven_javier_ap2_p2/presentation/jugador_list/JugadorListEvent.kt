package edu.ucne.steven_javier_ap2_p2.presentation.jugador_list

sealed interface JugadorListEvent {
    data object Refresh : JugadorListEvent
}