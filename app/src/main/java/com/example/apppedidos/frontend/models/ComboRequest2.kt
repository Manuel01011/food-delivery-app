package com.example.apppedidos.frontend.models

import com.google.gson.annotations.SerializedName

data class ComboRequest2(
    @SerializedName("id_combo") val idCombo: Int,
    @SerializedName("cantidad") val cantidad: Int,
)