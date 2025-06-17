package com.example.apppedidos.frontend.models

data class PedidoClientee(
    val id_usuario: Int,
    val nombre_cliente: String,
    val correo_cliente: String,
    val id_pedido: Int,
    val fecha_pedido: String,
    val total: Double
)

data class ClienteTop(
    val id_usuario: Int,
    val nombre_cliente: String,
    val correo_cliente: String,
    val cantidad_pedidos: Int
)