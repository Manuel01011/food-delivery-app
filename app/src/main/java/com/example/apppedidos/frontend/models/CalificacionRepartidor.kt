package com.example.apppedidos.frontend.models
data class CalificacionRepartidor(
    val id_repartidor: Int,
    val nombre_repartidor: String,
    val puntaje_repartidor: Int,
    val comentario: String?,
    val queja: Boolean,
    val id_pedido: Int
)

data class CalificacionesRepartidoresResponse(
    val success: Boolean,
    val calificaciones: List<CalificacionRepartidor>,
    val total_calificaciones: Int,
    val fecha_reporte: Long
)