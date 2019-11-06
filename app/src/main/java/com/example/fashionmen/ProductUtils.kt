package com.example.fashionmen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi

class ProductUtils {

    fun decodeBase64(codigo: String): Bitmap {
        val byte:ByteArray = Base64.decode(codigo, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(byte, 0, byte.size)
    }

}