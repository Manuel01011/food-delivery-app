package com.example.apppedidos.frontend.api
import com.example.apppedidos.frontend.activities.repartidor.RepartidorRegistroRequest
import com.example.apppedidos.frontend.models.ClientesResponse
import com.example.apppedidos.frontend.models.ComboRequest
import com.example.apppedidos.frontend.models.PedidoRequest
import com.example.apppedidos.frontend.models.Repartidor
import com.example.apppedidos.frontend.models.Restaurante
import com.example.apppedidos.frontend.models.RestauranteRequest
import com.example.apppedidos.frontend.models.RestaurantesResponse
import com.example.apppedidos.frontend.models.Usuario
import com.example.apppedidos.frontend.models.UsuarioLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Query

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
    suspend fun verificarUsuario(@Path("cedula") cedula: String): Response<UsuarioLogin>

    // Metodos de restaurante
    @GET("api/Restaurantes")
    suspend fun obtenerRestaurantes(): Response<RestaurantesResponse>

    @POST("api/restaurantes")
    suspend fun registrarRestaurante(@Body restaurante: RestauranteRequest): Response<Map<String, Any>>

    @GET("api/restaurantes/{id}/combos")
    suspend fun obtenerCombos(@Path("id") idRestaurante: Int): Response<Map<String, Any>>

    @POST("api/restaurantes/{id}/combos")
    suspend fun registrarCombo(@Path("id") idRestaurante: Int, @Body combo: ComboRequest): Response<Map<String, Any>>

    // Métodos de pedidos
    @POST("api/pedidos/completo")   suspend fun crearPedidoCompleto(@Body request: PedidoRequest): Response<Map<String, Any>>

    @GET("pedidos/{id_pedido}/detalle") suspend fun obtenerDetallePedido(@Path("id_pedido") idPedido: Int, @Query("cliente") idCliente: Int): Response<Map<String, Any>>

    @POST("pedidos/calificar") suspend fun calificarPedido(@Body request: Map<String, Any>): Response<Map<String, Any>>

    //metodos de clientes
    @GET("api/clientes/{id}/pedidos-completos") suspend fun listarPedidosCliente(@Path("id") idCliente: Int, @Query("estado") estado: String? = null): Response<Map<String, Any>>
}


