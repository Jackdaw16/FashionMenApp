package com.example.fashionmen.fragments.catalog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionmen.ServicesFragment
import com.example.fashionmen.R
import com.example.fashionmen.models.Product

import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response


class FragmentCatalog : ServicesFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productList: ArrayList<Product>
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.listaProductos)

        progressBar = view.findViewById(R.id.progressBar)

        productList = ArrayList()

        recyclerView.adapter = AdapterCatalog(productList)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val productsCall = productService.getProducts()

        productsCall.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<List<Product>>,
                response: Response<List<Product>>) {
                productList.clear()
                productList.addAll(response.body()!!)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        })
    }
}
