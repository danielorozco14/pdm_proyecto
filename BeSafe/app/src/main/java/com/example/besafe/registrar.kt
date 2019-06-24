package com.example.besafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registrar.*

class registrar : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()
     var flag:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        registra()
        if(flag)
            Toast.makeText(this, "Registro Completado", Toast.LENGTH_LONG).show()
    }

    private fun registra() {

        var mCorreoRegistro = "ceciSeLaCome@mbienDoblada.com"// correo_registro.text.toString()
        var mContraRegistro = "PutoElQueLoLea"//contra_registro.text.toString()

        button_registro.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(mCorreoRegistro, mContraRegistro).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(Intent(this, BeSafe::class.java))
                    Log.d("SIGN UP", "creacionUsuarioConCorreo: hecho")
                    flag=true
                } else {
                    Toast.makeText(this, "Error registrandose", Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}
