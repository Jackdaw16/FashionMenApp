package com.example.fashionmen.services

import com.example.fashionmen.models.Order
import retrofit2.Call
import retrofit2.http.GET

interface OrderService {
    @GET("pedidos")
    fun getOrders(): Call<List<Order>>
}