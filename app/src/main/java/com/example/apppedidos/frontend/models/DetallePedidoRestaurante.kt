package com.example.apppedidos.frontend.models

data class DetallePedidoRestaurante(
    val id_pedido: Int,
    val fecha_pedido: String,
    val estado: String,
    val total: Double,
    val nombre_cliente: String,
    val direccion_cliente: String,
    val telefono_cliente: String,
    val nombre_repartidor: String?,
    val telefono_repartidor: String?,
    val detalles: List<DetallePedidoItem>
)

data class DetallePedidoItem(
    val combo: String,
    val cantidad: Int,
    val precio_unitario: Double,
    val subtotal: Double
)