package com.example.deliverynt.models

data class Comanda (
    var id : String,
    var restaurant : String,
    var numPackages : Int,
    var productes : Array<Product>,
    var location : Coordinates,
    var price: Double,
    val userMail: String,
    val estate: Estate
)