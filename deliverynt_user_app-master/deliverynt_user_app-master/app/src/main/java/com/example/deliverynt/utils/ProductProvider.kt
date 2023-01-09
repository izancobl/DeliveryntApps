package com.example.deliverynt.utils

import com.example.deliverynt.models.Product

class ProductProvider {
    companion object {
        val productList = listOf<Product>(
            Product(1, "Estrella Damm", 1, 0.9, 50, "drinks"),
            Product(1, "Cola Cao", 1, 0.99, 60, "drinks"),
            Product(1, "Atun Calvo", 1, 0.95, 30, "products"),
            Product(1, "AXE", 1, 1.9, 50, "products"),
            Product(1, "Estrella Galicia", 1, 1.9, 10, "drinks"),
            Product(1, "Lays Campesina", 1, 19.0, 50, "products"),
            Product(1, "Olivas", 1, 7.9, 5, "products"),
            Product(1, "Platanos", 1, 2.9, 4, "products"),
            Product(1, "Manzanas", 1, 3.9, 20, "products"),
            Product(1, "Agua Mineral", 1, 7.9, 35, "drinks"),
            Product(1, "Kinder Bueno", 1, 9.9, 70, "products"),
            Product(1, "Alpro", 1, 0.9, 50, "products"),
            Product(1, "Yo Pro", 1, 0.9, 50, "products"),
        )
    }
}