package com.example.fashionmen.models

import java.io.Serializable

data class Product (val id: Int, val nombre: String,
                    val precio: Double, val img: String,
                    val tipoProducto: String, val descripcion: String,
                    val tallas: String, val likes: Int
) : Serializable