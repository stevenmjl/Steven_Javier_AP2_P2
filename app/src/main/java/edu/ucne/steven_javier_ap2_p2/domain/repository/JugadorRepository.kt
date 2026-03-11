package edu.ucne.steven_javier_ap2_p2.domain.repository

import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador
import edu.ucne.steven_javier_ap2_p2.util.Resource

interface JugadorRepository {
    suspend fun getJugadores(): Resource<List<Jugador>>

    suspend fun getJugador(id: Int): Resource<Jugador>

    suspend fun saveJugador(jugador: Jugador): Resource<Jugador>

    suspend fun updateJugador(id: Int, jugador: Jugador): Resource<Jugador>
}