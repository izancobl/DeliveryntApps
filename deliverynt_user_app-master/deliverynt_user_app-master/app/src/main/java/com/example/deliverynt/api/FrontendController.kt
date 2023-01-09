package com.example.deliverynt.controllers

import com.example.deliverynt.models.Address
import com.example.deliverynt.models.Coordinates
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


var gson = GsonBuilder()
    .setLenient()
    .create()

object FrontEndController {
    private const val URL_BASE = "http://10.0.2.2:8080/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val service = retrofit.create<ApiService>(ApiService::class.java)

    fun getComandes(): String {
        var result:String = ""
        service.getAllComandes().enqueue(object : Callback<java.io.Serializable> {
            override fun onResponse (call:Call<java.io.Serializable>, response: Response<java.io.Serializable>) {
                val comandes = response?.body()
                result = Gson().toJson(comandes)
            }

            override fun onFailure(call:Call<java.io.Serializable>,t:Throwable) {
                t.printStackTrace()
            }
        })
        return result
    }
}