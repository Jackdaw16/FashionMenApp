package com.example.fashionmen.fragments.cart


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fashionmen.ServicesFragment

import com.example.fashionmen.R
import com.example.fashionmen.models.Order
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentCart : ServicesFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ordersCall = orderService.getOrders()

        ordersCall.enqueue(object : Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<List<Order>>,
                                    response: Response<List<Order>>
            ) {
                Log.v("retrofit", Gson().toJson(response.body()))
            }
        })
        return inflater.inflate(R.layout.fragment_cesta, container, false)
    }
}
