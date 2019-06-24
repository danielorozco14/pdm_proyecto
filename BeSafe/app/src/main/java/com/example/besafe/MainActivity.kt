package com.example.besafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binder()
    }

    fun binder() {
        logea()
        register.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

    }



    private fun logea() {

        login.setOnClickListener {
            var txtemail = email.text.toString()
            var txtpassword = password.text.toString()
            if (!txtemail.isEmpty() && !txtpassword.isEmpty()) {
               mAuth.signInWithEmailAndPassword(txtemail, txtpassword)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, BeSafe::class.java))
                            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error logging in", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
