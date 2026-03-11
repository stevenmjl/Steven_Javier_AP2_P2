package edu.ucne.steven_javier_ap2_p2.domain.use_case

import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador
import edu.ucne.steven_javier_ap2_p2.domain.repository.JugadorRepository
import edu.ucne.steven_javier_ap2_p2.util.Resource
import javax.inject.Inject

class GetJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int):
            Resource<Jugador> = repository.getJugador(id)
}