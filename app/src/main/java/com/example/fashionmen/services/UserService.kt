package com.example.fashionmen.services

import com.example.fashionmen.models.User
import com.example.fashionmen.models.viewmodels.LoggedUser
import com.example.fashionmen.models.viewmodels.LoginViewModel
import com.example.fashionmen.models.viewmodels.RegisterViewModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.PATCH as PATCH1

interface UserService {

    @POST("usuarios/login")
    fun login(@Body loginInfo: LoginViewModel): Call<LoggedUser>

    @POST("usuarios")
    fun register(@Body registerInfo: RegisterViewModel): Call<User>

    @PUT("usuarios/{id}")
    fun updateDataUser(@Path("id") userId : Int, @Body user: User): Call<User>
}