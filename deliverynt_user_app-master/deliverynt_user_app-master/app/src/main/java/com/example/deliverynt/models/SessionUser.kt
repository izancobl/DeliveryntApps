package com.example.deliverynt.models

import android.location.Location

object SessionUser {
    lateinit var userEmail: String
    lateinit var provider: String
    lateinit var local: String
    lateinit var location: Coordinates

    fun setSessionUser(userEmail: String, provider: String, local:String, location: Coordinates) {
        this.userEmail = userEmail
        this.provider = provider
        this.local = local
        this.location = location
    }
}