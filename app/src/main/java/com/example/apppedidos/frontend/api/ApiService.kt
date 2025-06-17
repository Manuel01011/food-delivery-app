package com.example.apppedidos.frontend.api
import com.example.apppedidos.frontend.activities.repartidor.RepartidorRegistroRequest
import com.example.apppedidos.frontend.models.CalificacionRequest
import com.example.apppedidos.frontend.models.CalificacionResponse
import com.example.apppedidos.frontend.models.CalificacionesRepartidoresResponse
import com.example.apppedidos.frontend.models.ClientesResponse
import com.example.apppedidos.frontend.models.ComboRequest
import com.example.apppedidos.frontend.models.PedidoRequest
import com.example.apppedidos.frontend.models.PedidoRestaurante
import com.example.apppedidos.frontend.models.PedidosResponse
import com.example.apppedidos.frontend.models.PedidosResponseRepartidor
import com.example.apppedidos.frontend.models.Repartidor
import com.example.apppedidos.frontend.models.ReporteVentasResponse
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
import retrofit2.http.PUT
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

    @GET("api/repartidores/{id}/pedidos") suspend fun obtenerPedidosRepartidor(@Path("id") idRepartidor: Int, @Query("estado") estado: String? = null): Response<PedidosResponseRepartidor>

    @PUT("api/pedidos/{idPedido}/estado") suspend fun actualizarEstadoPedidoRepartidor(@Path("idPedido") idPedido: Int, @Body request: Map<String, String>): Response<Map<String, Any>>

    // Métodos usuarios
    @POST("/api/usuarios")
    fun registrarUsuario(@Body usuario: Usuario): Call<Map<String, Any>>

    @GET("api/clientes")
    suspend fun obtenerClientes(): Response<ClientesResponse>

    @GET("api/usuarios/{cedula}")
    suspend fun verificarUsuario(@Path("cedula") cedula: String): Response<UsuarioLogin>

    @GET("api/reportes/clientes-pedidos") suspend fun obtenerClientesYPedidos(): Response<Map<String, Any>>

    // Metodos de restaurante
    @GET("api/Restaurantes")
    suspend fun obtenerRestaurantes(): Response<RestaurantesResponse>

    @POST("api/restaurantes")
    suspend fun registrarRestaurante(@Body restaurante: RestauranteRequest): Response<Map<String, Any>>

    @GET("api/restaurantes/{id}/combos")
    suspend fun obtenerCombos(@Path("id") idRestaurante: Int): Response<Map<String, Any>>

    @POST("api/restaurantes/{id}/combos")
    suspend fun registrarCombo(@Path("id") idRestaurante: Int, @Body combo: ComboRequest): Response<Map<String, Any>>

    @GET("api/reportes/restaurantes-populares") suspend fun obtenerRestaurantesPopulares(): Response<Map<String, Any>>

    @GET("api/reportes/ventas-restaurantes")  suspend fun obtenerReporteVentasRestaurantes(): Response<ReporteVentasResponse>

    // Métodos de pedidos
    @POST("api/pedidos/completo")   suspend fun crearPedidoCompleto(@Body request: PedidoRequest): Response<Map<String, Any>>

    @GET("pedidos/{id_pedido}/detalle") suspend fun obtenerDetallePedido(@Path("id_pedido") idPedido: Int, @Query("cliente") idCliente: Int): Response<Map<String, Any>>

    @POST("api/pedidos/calificar") suspend fun calificarPedido(@Body request: CalificacionRequest): Response<CalificacionResponse>

    //metodos de clientes
    @GET("api/clientes/{id}/pedidos-completos") suspend fun listarPedidosCliente(@Path("id") idCliente: Int, @Query("estado") estado: String? = null): Response<Map<String, Any>>

    // Metodos rol restaurante
    @GET("api/restaurantes/{id}/pedidos")
    suspend fun listarPedidosRestaurante(@Path("id") idRestaurante: Int, @Query("estado") estado: String? = null): Response<PedidosResponse>

    @GET("api/restaurantes/{idRestaurante}/pedidos/{idPedido}") suspend fun obtenerDetallePedidoRestaurante(@Path("idRestaurante") idRestaurante: Int, @Path("idPedido") idPedido: Int): Response<Map<String, Any>>

    @PUT("api/restaurantes/{idRestaurante}/pedidos/{idPedido}/estado") suspend fun actualizarEstadoPedido(@Path("idRestaurante") idRestaurante: Int, @Path("idPedido") idPedido: Int, @Body request: Map<String, String>): Response<Map<String, Any>>

    // Métodos de calificaciones
    @GET("api/reportes/calificaciones-repartidores") suspend fun obtenerCalificacionesRepartidores(): Response<CalificacionesRepartidoresResponse>
}


