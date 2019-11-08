package com.example.fashionmen.activities.productDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.example.fashionmen.R
import com.example.fashionmen.models.Product

class ActivityProductDetail : AppCompatActivity() {

    private lateinit var textViewPrice: AppCompatTextView
    private lateinit var textViewDescription: AppCompatTextView
    private lateinit var imageView: ImageView
    private lateinit var product: Product
    private lateinit var imageButton: AppCompatImageButton
    private lateinit var listaTallas: List<String>
    private lateinit var radioGroup: RadioGroup
    private lateinit var imageButtonRemove: ImageButton
    private lateinit var imageButtonAdd: ImageButton
    private lateinit var textViewContador: AppCompatTextView

    private lateinit var btnBuy: Button
    private lateinit var btnSendCard: Button

    private var bundleKey: String = "product"
    private var delimiter: String = ","
    private var contador: Int = 1
    private lateinit var elementoSeleccionado: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        textViewPrice = findViewById(R.id.precioProducto)
        textViewDescription = findViewById(R.id.descripcionProducto)
        imageView = findViewById(R.id.imagenProducto)
        imageButton = findViewById(R.id.backButton)
        radioGroup = findViewById(R.id.comboOpciones)
        imageButtonAdd = findViewById(R.id.aumentar)
        imageButtonRemove = findViewById(R.id.reducir)
        textViewContador = findViewById(R.id.contador)
        btnBuy = findViewById(R.id.botonComprar)

        imageButton.setOnClickListener{ returnLastActivity() }
        imageButtonRemove.setOnClickListener{ removeOne() }
        imageButtonAdd.setOnClickListener { addOne() }

        product = intent.getSerializableExtra(bundleKey) as Product

        val precioTexto = "${product.precio} €"

        textViewPrice.text = precioTexto
        textViewDescription.text = product.descripcion
        Glide.with(this).load(product.img).into(imageView)
        textViewContador.text = contador.toString()

        listaTallas = product.tallas.split(delimiter.toRegex())

        listaTallas.forEach{
            val radioButton = RadioButton(this)
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(170, LinearLayout.LayoutParams.MATCH_PARENT)
            layoutParams.marginEnd = 20

            radioButton.text = it
            radioButton.background = getDrawable(R.drawable.rb_texture)
            radioButton.setButtonDrawable(android.R.color.transparent)
            radioButton.layoutParams = layoutParams
            radioButton.gravity = Gravity.CENTER
            radioButton.setTextColor(getColor(R.color.colorWhite))

            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            elementoSeleccionado = radioButton.text.toString()
            println(radioButton.text.toString())
        }
    }

    private fun addOne(){
        contador += 1
        textViewContador.text = contador.toString()
    }

    private fun removeOne(){
        if (contador > 1) {
            contador -= 1
            textViewContador.text = contador.toString()
        } else {
            return
        }
    }
    private fun returnLastActivity(){
        finish()
    }
}

