package edu.ucne.steven_javier_ap2_p2.domain.use_case

import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador
import edu.ucne.steven_javier_ap2_p2.domain.repository.JugadorRepository
import edu.ucne.steven_javier_ap2_p2.util.Resource
import javax.inject.Inject

class SaveJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador):
            Resource<Jugador> = repository.saveJugador(jugador)

    suspend fun update(id: Int, jugador: Jugador):
            Resource<Unit> = repository.updateJugador(id, jugador)
}