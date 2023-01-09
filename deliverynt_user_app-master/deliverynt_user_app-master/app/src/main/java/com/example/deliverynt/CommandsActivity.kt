package com.example.deliverynt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverynt.adapters.CommandAdapter
import com.example.deliverynt.adapters.ProductAdapter
import com.example.deliverynt.controllers.FrontEndController
import com.example.deliverynt.databinding.ActivityCommandsBinding
import com.example.deliverynt.databinding.ActivityProductsBinding
import com.example.deliverynt.models.*
import com.example.deliverynt.utils.ProductProvider
import retrofit2.Response

class CommandsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommandsBinding
    private lateinit var shopButton: ImageView
    lateinit var commands: List<Comanda>
    lateinit var adapter: CommandAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommandsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
    }

    private fun initRecycleView() {
        shopButton = binding.stButton
        getComandes(SessionUser.userEmail)
        if(commands == null) commands= listOf()
        adapter = CommandAdapter(commands)
        binding.recyclerCommands.layoutManager = LinearLayoutManager(this)
        binding.recyclerCommands.adapter = adapter

        shopButton.setOnClickListener {
            showDistActivity()
        }
    }

    private fun showDistActivity() {
        val storageIntent = Intent(this, StorageActivity::class.java)
        startActivity(storageIntent)
    }

    private fun getComandes(email: String){
        disableThreadStrictPolicy();
        val response: Response<List<Comanda>>? = FrontEndController.service.getComandaEmail(email).execute()
        try {
            if (response != null) {
                commands = response.body()!!
            }
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this,"Api Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun disableThreadStrictPolicy() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}