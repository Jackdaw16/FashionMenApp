package com.example.fashionmen.models.viewmodels

import com.example.fashionmen.models.User

data class LoggedUser(
    val user: User,
    val accessToken: String
)