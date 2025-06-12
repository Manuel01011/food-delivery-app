package com.example.apppedidos.frontend.models

data class RestauranteRequest(
    val nombre: String,
    val cedula_juridica: String,
    val direccion: String,
    val tipo_comida: String
)