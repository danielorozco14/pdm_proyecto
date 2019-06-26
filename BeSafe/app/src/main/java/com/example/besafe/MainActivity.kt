package com.example.besafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
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
            register.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein))
            startActivity(Intent(this, RegistroActivity::class.java))
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        }

    }


    private fun logea() {


        login.setOnClickListener {
            login.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein))
            if(email.text.toString()!=""&&password.text.toString()!=""){

                var txtemail = email.text.toString()
                var txtpassword = password.text.toString()

                mAuth.signInWithEmailAndPassword(txtemail, txtpassword).addOnCompleteListener (this){
                    if (it.isSuccessful) {
                        startActivity(Intent(this, FormsActivity::class.java))
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                        Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error logging in", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }
    }
}
