package com.example.fashionmen.fragments.catalog

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionmen.models.Product
import com.example.fashionmen.ProductUtils
import com.example.fashionmen.R
import com.example.fashionmen.activities.productDetail.ActivityProductDetail

class AdapterCatalog(private val ListaProductos: List<Product>?): RecyclerView.Adapter<AdapterCatalog.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private lateinit var currentProduct: Product
        private var currentPosition: Int = 0
        val imageProduct: ImageView = itemView.findViewById(R.id.productImg)
        val productName: AppCompatTextView = itemView.findViewById(R.id.nameProduct)
        val priceProduct: AppCompatTextView = itemView.findViewById(R.id.priceProducto)

        init {
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, ActivityProductDetail::class.java)
                intent.putExtra("product", currentProduct)
                itemView.context.startActivity(intent)
            }
        }

        fun setData(product: Product, position: Int){
            this.currentPosition = position
            this.currentProduct = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.productos_adapter, parent, false)


        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return ListaProductos!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = ListaProductos?.get(position)!!
        val funcion = ProductUtils()
        val precioConSimbolo = product.precio.toString() + "€"

        //holder.imageProduct.setImageBitmap(funcion.decodeBase64(product.img))
        Glide.with(holder.itemView.context).load(product.img).into(holder.imageProduct)
        holder.productName.text = product.nombre
        holder.priceProduct.text = precioConSimbolo

        holder.setData(product, position)
    }
}