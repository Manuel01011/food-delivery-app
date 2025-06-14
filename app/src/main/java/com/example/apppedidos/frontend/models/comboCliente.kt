package com.example.apppedidos.frontend.models

data class ComboCliente(
    val id_combo: Int,
    val id_restaurante: Int,
    val numero_combo: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val activo: Boolean
)