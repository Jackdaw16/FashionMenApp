package com.example.fashionmen.models.viewmodels

import com.example.fashionmen.models.User

data class LoggedUser(
    var user: User,
    var accessToken: String
)