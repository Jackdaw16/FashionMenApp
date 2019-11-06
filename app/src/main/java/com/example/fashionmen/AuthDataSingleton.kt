package com.example.fashionmen

import androidx.preference.PreferenceManager
import com.example.fashionmen.models.viewmodels.LoggedUser
import com.google.gson.Gson


object AuthDataSingleton {
    var loggedUser = Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(FashionMenApplication.applicationContext()).getString("loggedUser", ""), LoggedUser::class.java)
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(FashionMenApplication.applicationContext())
                .edit()
                .putString("loggedUser", Gson().toJson(value))
                .apply()
        }
}