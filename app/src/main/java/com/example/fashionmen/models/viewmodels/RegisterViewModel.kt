package com.example.fashionmen.models.viewmodels

data class RegisterViewModel (
    val dni: String, val nombre_usuario: String,
    val passwd: String, val nombre_completo: String,
    val correo_electronico: String, val direccion: String
)