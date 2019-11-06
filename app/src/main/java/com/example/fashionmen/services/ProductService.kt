package com.example.fashionmen.services

import com.example.fashionmen.models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("productos")
    fun getProducts(): Call<List<Product>>
}