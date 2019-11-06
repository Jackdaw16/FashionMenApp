package com.example.fashionmen

import android.app.Application
import android.content.Context

class FashionMenApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: FashionMenApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = FashionMenApplication.applicationContext()
    }
}