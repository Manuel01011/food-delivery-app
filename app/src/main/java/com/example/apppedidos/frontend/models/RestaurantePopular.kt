package com.example.apppedidos.frontend.models

data class RestaurantePopular(
    val id_restaurante: Int,
    val nombre: String,
    val tipo_comida: String,
    val total_pedidos: Int,
    val ranking: Int
)

data class RestaurantesPopularesResponse(
    val success: Boolean,
    val restaurantes: List<RestaurantePopular>,
    val total_restaurantes: Int,
    val fecha_reporte: Long
)