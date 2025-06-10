package com.example.apppedidos.frontend.api

import com.example.apppedidos.frontend.activities.repartidor.RepartidorRegistroRequest
import com.example.apppedidos.frontend.models.Repartidor
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path

interface ApiService {
    @POST("api/repartidores")
    suspend fun registrarRepartidor(@Body repartidor: RepartidorRegistroRequest): Response<Map<String, Any>>

    @GET("api/repartidores")
    suspend fun obtenerRepartidores(): Response<RepartidorResponse>

    @GET("api/repartidores/cero-amorestaciones")
    suspend fun obtenerRepartidoresCeroAmonestaciones(): Response<List<Repartidor>>

    @POST("api/repartidores/{id}/amonestacion")
    suspend fun asignarAmonestacion(@Path("id") idRepartidor: Int): Response<Map<String, Int>>
}

