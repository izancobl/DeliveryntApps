package com.example.deliverynt.models

data class User(val email: String, val provider: String, val local: String, val location: Coordinates)

