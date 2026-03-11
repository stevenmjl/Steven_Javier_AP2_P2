package edu.ucne.steven_javier_ap2_p2.data.repository

import edu.ucne.steven_javier_ap2_p2.data.remote.JugadorApi
import edu.ucne.steven_javier_ap2_p2.data.remote.dto.JugadorDto
import edu.ucne.steven_javier_ap2_p2.data.remote.dto.toDomain
import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador
import edu.ucne.steven_javier_ap2_p2.domain.repository.JugadorRepository
import edu.ucne.steven_javier_ap2_p2.util.Resource
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val api: JugadorApi
) : JugadorRepository {

    override suspend fun getJugadores(): Resource<List<Jugador>> {
        return try {
            val response = api.getJugadores()
            val jugadores = response.map { it.toDomain() }
            Resource.Success(jugadores)
        } catch (e: Exception) {
            Resource.Error("Error conexión: ${e.localizedMessage}")
        }
    }

    override suspend fun getJugador(id: Int): Resource<Jugador> {
        return try {
            val response = api.getJugador(id)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body.toDomain())
            } else {
                Resource.Error("Jugador no encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")
        }
    }

    override suspend fun saveJugador(jugador: Jugador): Resource<Jugador> {
        return try {
            val jugadorDto = JugadorDto(
                nombres = jugador.nombres,
                email = jugador.email
            )
            val response = api.saveJugador(jugadorDto)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body.toDomain())
            } else {
                Resource.Error("Error al guardar: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error de red: ${e.message}")
        }
    }

    override suspend fun updateJugador(id: Int, jugador: Jugador): Resource<Unit> {
        return try {
            val jugadorDto = JugadorDto(
                jugadorId = id,
                nombres = jugador.nombres,
                email = jugador.email
            )
            val response = api.updateJugador(id, jugadorDto)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Error al actualizar: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error de red: ${e.message}")
        }
    }
}