package com.example.apppedidos.frontend.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Combo(
    val id_combo: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double
): Parcelable {
}