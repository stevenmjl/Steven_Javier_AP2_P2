package edu.ucne.steven_javier_ap2_p2.data.remote

import edu.ucne.steven_javier_ap2_p2.data.remote.dto.JugadorDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JugadorApi {
    @GET("api/Jugadores")
    suspend fun getJugadores(): List<JugadorDto>

    @GET("api/Jugadores/{id}")
    suspend fun getJugador(@Path("id") id: Int): Response<JugadorDto>

    @POST("api/Jugadores")
    suspend fun saveJugador(@Body jugadorDto: JugadorDto): Response<JugadorDto>

    @PUT("api/Jugadores/{id}")
    suspend fun updateJugador(
        @Path("id") id: Int,
        @Body jugadorDto: JugadorDto
    ): Response<Unit>
}