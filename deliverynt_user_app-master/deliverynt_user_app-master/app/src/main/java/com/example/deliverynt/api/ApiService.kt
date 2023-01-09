package com.example.deliverynt.controllers

import com.example.deliverynt.models.Address
import com.example.deliverynt.models.Comanda
import com.example.deliverynt.models.Coordinates
import com.example.deliverynt.models.User
import com.example.deliverynt.utils.AuthToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @Headers("Authorization: $AuthToken")
    @GET("comandes/")
    fun getAllComandes():Call<java.io.Serializable>

    @Headers("Authorization: $AuthToken")
    @POST("routes/coord/")
    fun getCoordFromAddress(@Body body: Address):Call<Coordinates>

    @Headers("Authorization: $AuthToken")
    @POST("users/")
    fun postUser(@Body body: User):Call<String>

    @Headers("Authorization: $AuthToken")
    @POST("comandes/")
    fun postComanda(@Body body: Comanda):Call<String>

    @Headers("Authorization: $AuthToken")
    @GET("comandes/{email}")
    fun getComandaEmail(@Path("email") email: String):Call<List<Comanda>>
}