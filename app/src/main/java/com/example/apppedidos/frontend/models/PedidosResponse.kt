package com.example.apppedidos.frontend.models

import com.google.gson.annotations.SerializedName

data class PedidosResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("pedidos") val pedidos: List<PedidoRestaurante>
)