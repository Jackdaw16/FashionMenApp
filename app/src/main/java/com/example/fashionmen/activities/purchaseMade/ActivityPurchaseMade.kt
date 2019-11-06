package com.example.fashionmen.activities.purchaseMade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fashionmen.R

class ActivityPurchaseMade : AppCompatActivity() {

    private lateinit var btnReturn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra_relizada)

        btnReturn = findViewById(R.id.btn_return)
        btnReturn.setOnClickListener { finish() }
    }
}
