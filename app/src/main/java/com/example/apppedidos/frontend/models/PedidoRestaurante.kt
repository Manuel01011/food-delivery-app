package com.example.apppedidos.frontend.models

import com.google.gson.annotations.SerializedName

data class PedidoRestaurante(
    @SerializedName("id_pedido") val id_pedido: Int,
    @SerializedName("fecha_pedido") val fecha_pedido: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("total") val total: Double,
    @SerializedName("nombre_cliente") val nombre_cliente: String,
    @SerializedName("direccion_cliente") val direccion_cliente: String,
    @SerializedName("nombre_repartidor") val nombre_repartidor: String,
    @SerializedName("cantidad_items") val cantidad_items: Int
)