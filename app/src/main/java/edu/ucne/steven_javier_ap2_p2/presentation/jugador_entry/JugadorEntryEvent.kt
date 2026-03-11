package edu.ucne.steven_javier_ap2_p2.presentation.jugador_entry

sealed interface JugadorEntryEvent {
    data class OnNombresChange(val nombres: String) : JugadorEntryEvent
    data class OnEmailChange(val email: String) : JugadorEntryEvent
    data object Save : JugadorEntryEvent
}