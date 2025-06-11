package com.example.apppedidos.frontend.models

data class Restaurante(
    val id_restaurante: Int = 0,
    val nombre: String,
    val cedula_juridica: String,
    val direccion: String,
    val tipo_comida: String
)