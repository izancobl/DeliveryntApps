package com.example.deliverynt.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.deliverynt.R
import com.example.deliverynt.databinding.ItemProductBinding
import com.example.deliverynt.models.Product

class ProductViewHolder (view: View) : ViewHolder(view) {
    private val binding = ItemProductBinding.bind(view)
    private var images:  HashMap<String, Int> = hashMapOf("drinks" to R.drawable.drinks, "products" to R.drawable.products)

    @SuppressLint("SetTextI18n")
    fun render(product: Product) {
        binding.productName.text = product.name
        binding.productPrice.text = "${product.price} €"
        binding.quantity.hint = "Max. u. ${product.quantity}"
        binding.ivProduct.setImageResource(images[product.photo] ?: R.drawable.drinks)
    }

    fun setProduct(product: Product): Product {
        val quantity: String = if (binding.quantity.text.toString() != "") binding.quantity.text.toString() else "0"
        val quanNum: Int = quantity.toInt()
        return Product(1, product.name, product.size, product.price, quanNum, "")
    }
}