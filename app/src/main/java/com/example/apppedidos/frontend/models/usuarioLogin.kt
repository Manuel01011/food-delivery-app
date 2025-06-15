package com.example.apppedidos.frontend.models

data class UsuarioLogin(
    val id_usuario: Int,
    val cedula: String,
    val nombre: String,
    val tipo: String,
    val estado: String,
    val origen: String
)