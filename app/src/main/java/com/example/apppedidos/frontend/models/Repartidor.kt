package com.example.apppedidos.frontend.models

data class Repartidor(
    val id_repartidor: Int = 0,
    val id_usuario: Int,
    val nombre: String,
    val estado_disponibilidad: String = "disponible",
    val distancia_pedido: Double = 0.0,
    val km_recorridos_diarios: Double = 0.0,
    val costo_por_km: Double = 0.0,
    val amonestaciones: Int = 0,
    val calificacion_promedio: Double = 0.0

) {
    // Propiedades computadas para mantener compatibilidad
    val cedula: String get() = id_usuario.toString()
    val estado: String get() = estado_disponibilidad
    val kmRecorridos: Double get() = km_recorridos_diarios
}