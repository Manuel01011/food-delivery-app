package com.example.apppedidos.frontend.api
import com.example.apppedidos.frontend.activities.repartidor.RepartidorRegistroRequest
import com.example.apppedidos.frontend.models.ClientesResponse
import com.example.apppedidos.frontend.models.Repartidor
import com.example.apppedidos.frontend.models.Restaurante
import com.example.apppedidos.frontend.models.RestaurantesResponse
import com.example.apppedidos.frontend.models.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.Call

interface ApiService {

    // Métodos de repartidores
    @POST("api/repartidores")
    suspend fun registrarRepartidor(@Body repartidor: RepartidorRegistroRequest): Response<Map<String, Any>>

    @GET("api/repartidores")
    suspend fun obtenerRepartidores(): Response<RepartidorResponse>

    @GET("api/repartidores/cero-amorestaciones")
    suspend fun obtenerRepartidoresCeroAmonestaciones(): Response<List<Repartidor>>

    @POST("api/repartidores/{id}/amonestacion")
    suspend fun asignarAmonestacion(@Path("id") idRepartidor: Int): Response<Map<String, Int>>

    // Métodos usuarios
    @POST("/api/usuarios")
    fun registrarUsuario(@Body usuario: Usuario): Call<Map<String, Any>>

    @GET("api/clientes")
    suspend fun obtenerClientes(): Response<ClientesResponse>

    @GET("api/usuarios/{cedula}")
    suspend fun verificarUsuario(@Path("cedula") cedula: String): Response<Usuario>

    // Metodos de restaurante
    @GET("api/Restaurantes")
    suspend fun obtenerRestaurantes(): Response<RestaurantesResponse>

    @POST("api/restaurantes")
    suspend fun registrarRestaurante(@Body restaurante: Restaurante): Response<Map<String, Any>>

    @GET("api/restaurantes/{id}/combos")
    suspend fun obtenerCombos(@Path("id") idRestaurante: Int): Response<Map<String, Any>>
}

