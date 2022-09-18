package com.example.barbershop.ui

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Corte(
    @get:Exclude var id: String = "",
    var nombre: String,
    var descripcion: String = "",
    var photoUrl: String,
    var Sexo: String,
    var like: Map<String, Boolean> = mutableMapOf()
)
