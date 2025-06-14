package com.example.apppedidos.frontend.models

import com.google.gson.annotations.SerializedName

data class PedidoRequest(
    @SerializedName("id_cliente") val idCliente: Int,
    @SerializedName("id_restaurante") val idRestaurante: Int,
    @SerializedName("direccion_entrega") val direccionEntrega: String,
    @SerializedName("combos") val combos: List<ComboRequest2>
)