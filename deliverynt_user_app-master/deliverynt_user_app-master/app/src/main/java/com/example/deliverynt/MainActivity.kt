package com.example.deliverynt
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    lateinit var googleButton: TextView
    lateinit var register: TextView
    lateinit var login: TextView
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var authLayout: RelativeLayout
    private val GOOGLE_SIGNIN = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        // Init Splash
        val screenSplash = installSplashScreen()

        // init
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Splash
        Thread.sleep(2000)
        screenSplash.setKeepOnScreenCondition { false }

        // Setup
        setUp()
        session()
    }

    private fun setUp() {
        // Setup Variables
        register = findViewById(R.id.register)
        login = findViewById(R.id.login)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        googleButton = findViewById(R.id.google)

        // Button Listeners
        register.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showCreateUserActivity(it.result.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        Toast.makeText(this, "Error de autentificación", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        login.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showStorageActivity(it.result.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        Toast.makeText(this, "Error de autentificación", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        googleButton.setOnClickListener {
            // Config
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().build()

            //Build a client with the options
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGNIN)
        }
    }

    override fun onStart() {
        super.onStart()
        authLayout = findViewById(R.id.authLayout)
        authLayout.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs: SharedPreferences? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email: String? = prefs?.getString("email", null)
        val provider: String? = prefs?.getString("provider", null)

        if(email != null && provider != null){
            authLayout = findViewById(R.id.authLayout)
            authLayout.visibility = View.INVISIBLE
            showStorageActivity(email, ProviderType.valueOf(provider))
        }

    }

    private fun showStorageActivity(email: String, provider: ProviderType) {
        val storageIntent = Intent(this, StorageActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(storageIntent)
    }

   private fun showCreateUserActivity(email: String, provider: ProviderType) {
        val createUserIntent = Intent(this, CreateUserActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(createUserIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de autentificación")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGNIN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showStorageActivity(account.email ?: "" , ProviderType.GOOGLE)
                        } else {
                            Toast.makeText(this, "Error de autentificación", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }

        }
    }
}