package com.example.besafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.cv_form_list_item.*


/**
 * YA FUNCIONA LA OBTENCION DE DATOS EN TIEMPO REAL, AUNQUE SOLO PARA UN DOCUMENTO ESPECIFICO DE LA COLECCION
 */
class FormsActivity : AppCompatActivity() {
    val TAG= "FIRESTORE"
    var db = FirebaseFirestore.getInstance()
    lateinit var Doc:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cv_form_list_item)


        val TAG="/D"
        // Add a new document with a generated ID



        addInfo()
        loadInfo()
    }

    fun addInfo(){
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )
        //SI SE USA ADD() FIRESTORE GENERA EL PROPIO ID DE EL DOCUMENTO, USANDO .DOCUMENT() Y SET() LO DEFINIMOS NOSOTROS
        db.collection("users")
            .document("info")
            .set(user)
            .addOnSuccessListener { documentReference ->

                Log.d(TAG, "DocumentSnapshot added ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }



    fun loadInfo(){
        var mDocRef= db.document("users/info")
         mDocRef.get().addOnCompleteListener(OnCompleteListener <DocumentSnapshot>{ task ->
             if(task.isSuccessful){
                 val document = task.result
                 if(document!=null){
                     var first=document.getString("first")+"\n"+
                             document.getString("last")+"\n"+document.get("born")
                     text_Test.text = first
                 }else{
                     Log.d(TAG,"No existe el documento")
                 }
             }else{
                 Log.d(TAG,"Hubo fallo en ",task.exception)
             }
         })
    }

    fun loadRealTimeInfo(){
        var mDocRef= db.document("users/info")
        mDocRef.addSnapshotListener(this,EventListener<DocumentSnapshot>{
            snap, e->
            if(e!=null){
                Log.e(TAG,"error",e)
            }else{
                var first= snap?.getString("first") +"\n"+
                        snap?.getString("last")+"\n"+snap?.get("born")
                text_Test.text = first
            }
        })
    }

    override fun onStart() {
        super.onStart()
        loadRealTimeInfo()
    }


}
