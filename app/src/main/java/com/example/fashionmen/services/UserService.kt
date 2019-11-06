package com.example.fashionmen.services

import com.example.fashionmen.models.viewmodels.LoggedUser
import com.example.fashionmen.models.viewmodels.LoginViewModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("usuarios/login")
    fun login(@Body loginInfo: LoginViewModel): Call<LoggedUser>

}