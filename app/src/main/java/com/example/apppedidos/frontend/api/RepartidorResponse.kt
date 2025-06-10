package com.example.apppedidos.frontend.api

import com.example.apppedidos.frontend.models.Repartidor

data class RepartidorResponse(
    val success: Boolean,
    val repartidores: List<Repartidor>
)