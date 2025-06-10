package com.example.apppedidos.frontend.models

data class Usuario(
    val cedula: String,
    val nombre: String,
    val direccion: String,
    val numero_tarjeta: String,
    val telefono: String,
    val correo: String,
    val tipo: String = "cliente"
)