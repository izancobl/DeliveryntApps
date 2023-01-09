package com.example.deliverynt

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverynt.controllers.FrontEndController
import com.example.deliverynt.models.Address
import com.example.deliverynt.models.Coordinates
import com.example.deliverynt.models.SessionUser
import com.example.deliverynt.models.User
import retrofit2.Response


class CreateUserActivity: AppCompatActivity() {
    lateinit var propertyName:EditText
    lateinit var propertyLocation:EditText
    lateinit var submitButton:TextView
    var responseCoord: Coordinates? = null

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        //Setup
        setUp()
    }

    // CLASS FUNCTION
    private fun setUp(){
        //SetupVariables
        propertyName=findViewById(R.id.localName)
        propertyLocation=findViewById(R.id.location)
        submitButton=findViewById(R.id.submitBtn)

        //ButtonListeners
        submitButton.setOnClickListener{
            if(propertyName.text.isNotEmpty() && propertyLocation.text.isNotEmpty()){
                getCoordFromAddress(Address(propertyLocation.text.toString()))

                if (responseCoord != null) {
                    val bundle: Bundle? = intent.extras
                    val email = bundle?.getString("email") ?: ""
                    val provider = bundle?.getString("provider") ?: ""
                    storeUserInfo(email, provider)
                    showStorageActivity(email ?: "", provider ?: "")
                }
                else {
                    Toast.makeText(this,"Invalid Coordinates",Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(this,"Empty parameters",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun storeUserInfo(email: String, provider: String) {
        // Session User
        responseCoord?.let {
            SessionUser.setSessionUser(email, provider, propertyName.text.toString(),
                it
            )
        }
        // Post User DB
        postUserInfo(User(email,provider,propertyName.text.toString(),responseCoord ?: Coordinates(0.0,0.0)))
    }

    private fun showStorageActivity(email: String, provider: String) {
        val storageIntent = Intent(this, StorageActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider)
        }
        startActivity(storageIntent)
    }

    // API CALLS
    private fun getCoordFromAddress(address: Address) {
        disableThreadStrictPolicy();
        val response: Response<Coordinates>? = FrontEndController.service.getCoordFromAddress(address).execute()
        try {
            if (response != null) {
                responseCoord = response?.body()
            }
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this,"Api Error",Toast.LENGTH_LONG).show()
        }
    }


    private fun postUserInfo(user: User) {
        disableThreadStrictPolicy();
        val response: Response<String>? = FrontEndController.service.postUser(user).execute()
        try {
            if (response == null) {
                Toast.makeText(this,"User Api Error",Toast.LENGTH_LONG).show()
            }
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this,"Api Error",Toast.LENGTH_LONG).show()
        }
    }

    private fun disableThreadStrictPolicy() {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}