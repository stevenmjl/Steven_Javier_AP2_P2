package edu.ucne.steven_javier_ap2_p2.data.remote.dto

import com.squareup.moshi.Json
import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador

data class JugadorDto(
    @Json(name = "jugadorId") val jugadorId: Int? = null,
    @Json(name = "nombres") val nombres: String,
    @Json(name = "email") val email: String
)

fun JugadorDto.toDomain() = Jugador(
    jugadorId = jugadorId ?: 0,
    nombres = nombres,
    email = email
)