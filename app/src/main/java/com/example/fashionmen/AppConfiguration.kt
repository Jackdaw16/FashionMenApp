package com.example.fashionmen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class AppConfiguration {
    private var stringKeyUser: String = "usuarioMemoria"
    private var preferencesKey: String = "sharedPreferences"

    @SuppressLint("ApplySharedPref")
    fun guardarUsuario(datosUsuario: String, context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesKey, 0)

        val editor = sharedPreferences.edit()
        editor.putString(stringKeyUser, datosUsuario)
        editor.commit()
    }

    fun getUsuarioGuardado(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesKey, 0)

        return sharedPreferences.getString("loggedUser", "").toString()
    }

    fun borrarDatosUsuario(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesKey, 0)

        val editor = sharedPreferences.edit()
        editor.remove(stringKeyUser)
        editor.commit()
    }
}


