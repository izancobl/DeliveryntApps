package com.example.deliverynt.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.deliverynt.R
import com.example.deliverynt.databinding.ItemOrderBinding
import com.example.deliverynt.databinding.ItemProductBinding
import com.example.deliverynt.models.Comanda
import com.example.deliverynt.models.Estate
import com.example.deliverynt.models.Product

class ComandaViewHolder (view: View) : ViewHolder(view) {
    private val binding = ItemOrderBinding.bind(view)
    private var color:  HashMap<Estate, String> = hashMapOf(
        Estate.Created to "#E5CD30", Estate.Delivered to "#1F9EC6", Estate.Delivering to "#48BE40")

    @SuppressLint("SetTextI18n")
    fun render(product: Comanda, position: Int) {
        binding.productName.text = "Order Nª ${position+1}"
        binding.productPrice.text = "${product.price} €"
        binding.quantity.text = product.estate.toString().toUpperCase()
        binding.quantity.setTextColor(Color.parseColor("${color[product.estate]}"))
        binding.titlePrice.text = "The order size is: ${product.numPackages}"
    }
}