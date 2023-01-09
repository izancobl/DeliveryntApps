package com.example.deliverynt


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverynt.adapters.ProductAdapter
import com.example.deliverynt.adapters.ProductViewHolder
import com.example.deliverynt.controllers.FrontEndController
import com.example.deliverynt.databinding.ActivityMainBinding
import com.example.deliverynt.databinding.ActivityProductsBinding
import com.example.deliverynt.models.*
import com.example.deliverynt.utils.ProductProvider
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding
    private lateinit var paymentButton: Button
    private val products = ProductProvider.productList
    private  val adapter: ProductAdapter = ProductAdapter(products)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
    }

    private fun initRecycleView() {
        paymentButton = binding.payment
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter

        paymentButton.setOnClickListener {

            val comanda = createComanda()
            postComanda(comanda)
            showAlert()
        }
    }

    private fun createComanda(): Comanda {
        // Create id
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val currentDate = LocalDateTime.now().format(formatter)
        val id ="${SessionUser.userEmail}_$currentDate"

        // Create products
        val productsComanda: List<Product> = ferComanda(binding.recyclerProducts)
        val filteredProduct: List<Product> = productsComanda.filter { it.quantity > 0 }

        // Create size
        var size= 0
        var price = 0.0
        for(p in filteredProduct) {
            size += p.size*p.quantity
            price += p.price*p.quantity
        }

        return Comanda(id, SessionUser.local, size ,filteredProduct.toTypedArray(), SessionUser.location, price,SessionUser.userEmail,Estate.Created)
    }

    private fun ferComanda(recyclerView: RecyclerView): List<Product> {
        val list: MutableList<Product> = mutableListOf()
        val size: Int = recyclerView.childCount
        var init: Int = 0;
        while (init < size) {
            val holder: ProductViewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(init)) as ProductViewHolder
            list.add(holder.setProduct(products[init]))
            ++init
        }
        return list
    }

    private fun postComanda(comand: Comanda) {
        disableThreadStrictPolicy();
        val response: Response<String>? = FrontEndController.service.postComanda(comand).execute()
        try {
            if (response == null) {
                Toast.makeText(this,"User Api Error", Toast.LENGTH_LONG).show()
            }
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this,"Api Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Order Purchased")
        builder.setMessage("You're order has been transmitted")
        builder.setPositiveButton("Accept") { dialog, which ->
            showStorageActivity()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showStorageActivity() {
        val storageIntent = Intent(this, StorageActivity::class.java)
        startActivity(storageIntent)
    }

    private fun disableThreadStrictPolicy() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}