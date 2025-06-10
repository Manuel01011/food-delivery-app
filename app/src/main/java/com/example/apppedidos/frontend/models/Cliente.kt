package com.example.apppedidos.frontend.models

data class Cliente(
    val id_usuario: Int,
    val cedula: String,
    val nombre: String,
    val correo: String,
    val direccion: String,
    val tipo: String,
    val telefono: String,
    val estado: String,
    val pedidos: List<Any>
)

data class ClientesResponse(
    val success: Boolean,
    val clientes: List<Cliente>
)