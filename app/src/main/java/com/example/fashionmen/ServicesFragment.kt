package com.example.fashionmen

import androidx.fragment.app.Fragment
import com.example.fashionmen.services.OrderService
import com.example.fashionmen.services.ProductService
import com.example.fashionmen.services.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ServicesFragment : Fragment() {

    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${AuthDataSingleton.loggedUser?.accessToken ?: ""}")
            .build()
        chain.proceed(newRequest)
    }.build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://fashionmen.azurewebsites.net/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    protected var productService: ProductService
    protected var userService: UserService
    protected var orderService: OrderService

    init {
        productService = retrofit.create(ProductService::class.java)
        userService = retrofit.create(UserService::class.java)
        orderService = retrofit.create(OrderService::class.java)
    }

}