package com.example.apppedidos.frontend.models

data class PedidosResponseRepartidor(
    val success: Boolean,
    val pedidos: List<PedidoRepartidor>
)

data class PedidoRepartidor(
    val id_pedido: Int,
    val fecha_pedido: String,
    val estado: String,
    val total: Double,
    val restaurante: String,
    val direccion_restaurante: String,
    val cliente: String,
    val direccion_entrega: String,
    val cantidad_items: Int,
    val minutos_transcurridos: Int,
    val estado_descripcion: String,
    val ganancia_repartidor: Double
)