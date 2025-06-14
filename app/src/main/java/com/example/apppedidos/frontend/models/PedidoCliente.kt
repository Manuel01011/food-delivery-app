package com.example.apppedidos.frontend.models

data class PedidoCliente(
    val id_pedido: Int,
    val fecha_pedido: String,
    val estado: String,
    val total: Double,
    val restaurante: String,
    val tipo_comida: String,
    val id_repartidor: Int?,
    val nombre_repartidor: String?,
    val calificado: Boolean
)