package com.example.besafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.besafe.data.entities.Test
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class BeSafe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        database()
    }


    fun database(){
        var db = FirebaseFirestore.getInstance()
        //TODO:AGREGANDO DATOS A FIRESTORE

        var palabra = db.collection("test")//REFERENCIA A LA COLECCION EN FIRESTORES


        boton_enviar.setOnClickListener {
            var test= Test(palabra_text.text.toString())
            //val myMap = mapOf<String,Object>("word" to test)
            palabra.add(test)
        }





    }

}
