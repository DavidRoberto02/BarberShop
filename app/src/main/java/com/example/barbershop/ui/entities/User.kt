package com.example.barbershop.ui.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    @get:Exclude var id: String = "",
    var nombre: String = "",
    var descripcion: String = "",
    var photoUrl: String = "",
    var Sexo: String = ""
)
