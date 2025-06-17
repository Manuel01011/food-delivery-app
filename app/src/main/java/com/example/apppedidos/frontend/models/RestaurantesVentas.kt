package com.example.apppedidos.frontend.models

data class RestaurantesVentas(
    val idRestaurante: Int,
    val nombreRestaurante: String,
    val tipoComida: String,
    val totalVendido: Double,
    val porcentajeTotal: Double,
    val ventasTotalesGenerales: Double
)
data class ReporteVentasResponse(
    val success: Boolean,
    val reporte: List<RestaurantesVentas>
)