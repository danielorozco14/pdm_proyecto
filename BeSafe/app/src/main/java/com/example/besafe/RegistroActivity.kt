package com.example.besafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registrar.*

class RegistroActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        registra()
    }

    private fun registra() {

        button_registro.setOnClickListener {
            button_registro.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein))
            if (correo_registro.text.toString() != "" && contra_registro.text.toString() != "") {
                var mCorreoRegistro = correo_registro.text.toString()
                var mContraRegistro = contra_registro.text.toString()
                mAuth.createUserWithEmailAndPassword(mCorreoRegistro, mContraRegistro).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, BeSafe::class.java))
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        Toast.makeText(this, "Registro Completado", Toast.LENGTH_LONG).show()
                        Log.d("SIGN UP", "creacionUsuarioConCorreo: hecho")
                    } else {
                        Toast.makeText(this, "Error registrandose", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
