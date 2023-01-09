package com.example.deliverynt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC,
    GOOGLE
}

class StorageActivity : AppCompatActivity() {
    lateinit var signOutButton: Button
    lateinit var dammButton: TextView
    lateinit var commandButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        // Setup
        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setUp(email ?: "", provider ?: "")

        // Data storage
        val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email", email)
        prefs?.putString("provider", provider)
        prefs?.apply()
    }


    private fun setUp(email: String, provider: String) {
        // Init variables
        signOutButton = findViewById(R.id.signOut)
        dammButton = findViewById(R.id.damm_text_button)
        commandButton =findViewById(R.id.commandsButton)

        // Button Listener
        signOutButton.setOnClickListener {
            // Borrado Datos
            val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs?.clear()
            prefs?.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        dammButton.setOnClickListener {
            showProductActivity()
        }


        commandButton.setOnClickListener {
            showCommandActivity()
        }
    }

    private fun showProductActivity() {
        val storageIntent = Intent(this, ProductsActivity::class.java)
        startActivity(storageIntent)
    }

    private fun showCommandActivity() {
        val storageIntent = Intent(this, CommandsActivity::class.java)
        startActivity(storageIntent)
    }
}