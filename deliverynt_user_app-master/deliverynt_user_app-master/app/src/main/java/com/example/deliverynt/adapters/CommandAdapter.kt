package com.example.deliverynt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverynt.R
import com.example.deliverynt.models.Comanda
import com.example.deliverynt.models.Product

class CommandAdapter(private val productList: List<Comanda>) : RecyclerView.Adapter<ComandaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComandaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComandaViewHolder(layoutInflater.inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: ComandaViewHolder, position: Int) {
        val item = productList[position]
        holder.render(item, position)
    }

    override fun getItemCount(): Int = productList.size


}