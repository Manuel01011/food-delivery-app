package com.example.apppedidos.frontend.models

data class RestaurantesResponse(
    val success: Boolean,
    val restaurantes: List<Restaurante>
)