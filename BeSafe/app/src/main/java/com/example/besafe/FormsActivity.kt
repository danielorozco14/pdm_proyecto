package com.example.besafe


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.besafe.adapters.firestoreAdapter.FirestoreUsersAdapter
import com.example.besafe.data.entities.FormQ
import com.example.besafe.data.entities.Question
import com.example.besafe.data.entities.Users

import com.example.besafe.fragments.opcionesFragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_forms.*


/**
 * YA FUNCIONA LA OBTENCION DE DATOS EN TIEMPO REAL, AUNQUE SOLO PARA UN DOCUMENTO ESPECIFICO DE LA COLECCION
 * PARA PROBARLO, LUEGO DE QUE INICIE LA APLICACION VAN A LA CONSOLA DE FIRESTORE Y CAMBIAN LOS VALORES DE EL DOCUMENTO "INFO"
 */
class FormsActivity : AppCompatActivity() {
    val TAG = "FIRESTORE"
    var db = FirebaseFirestore.getInstance()


    var cont=0 //CONTADOR QUE SIRVE PARA INDEXAR EL DOCUMENTO, NOS SERA MAS FACIL OBTENER UN DOCUMENTO ESPECIFICO SI SABEMOS QUE NUMERO TIENE AL FINAL
    var aux=loadInfo();
    //private var adapter :UsersFirestoreRecyclerAdapter?=null
    lateinit var adapter: FirestoreUsersAdapter


    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forms)
        setSupportActionBar(maintoolbar as Toolbar?)
        //setContentView(R.layout.opciones_fragment)

        showOpcionesFragment()



        addInfo()
        /**
        var recycler_view = findViewById<RecyclerView>(R.id.rv_forms)


        recycler_view.layoutManager = LinearLayoutManager(this)
        val query = db.collection("users").orderBy("first", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Users>().setQuery(query, Users::class.java).build()
        //adapter = UsersFirestoreRecyclerAdapter(options)
        adapter = FirestoreUsersAdapter(options)
        recycler_view.adapter = adapter

        **/
        var uidtoken = mAuth.currentUser?.uid.toString()

        val formQ = FormQ(uidtoken, 10)

        val arrayString: ArrayList<String> = ArrayList()
        val arrayNumber: ArrayList<Int> = ArrayList()
        val Question: Question

        for (i in 1..5) {
            arrayString.add(arrayString.size, i.toString())
            arrayNumber.add(arrayNumber.size, i)
        }

        Question = Question(arrayString, arrayNumber, 1, "ontas")

        db.collection("formq").add(formQ).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            db.collection("formq").document(documentReference.id).collection("question").add(Question)
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


        //loadInfo()

    }





    override fun onStart() {
        super.onStart()
        //loadRealTimeInfo()

        //adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        /**if (adapter != null) {
          adapter!!.stopListening()
       }**/
    }

    fun addInfo() {
        cont++
       val user = hashMapOf(
            "first" to "James",
            "last" to "Bond",
            "born" to 1957
        )
        //SI SE USA ADD() FIRESTORE GENERA EL PROPIO ID DE EL DOCUMENTO, USANDO .DOCUMENT() Y SET() LO DEFINIMOS NOSOTROS
        //TODO TAL VEZ TENGA QUE REGRESAR ESTO A ADD() PARA QUE FUNCIONE EL RECYCLER VIEW

        db.collection("users")
            .add(user)//.document("persona_${aux}")//ID DEL DOCUMENTO
            //.set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun loadInfo() :Int {
        /**
        var mDocRef = db.document("users/persona_1")
        mDocRef.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    var first = document.getString("first") + "\n" +
                            document.getString("last") + "\n" + document.get("born")
                    var text_Test = findViewById<TextView>(R.id.text_Test1)
                    text_Test.text = first
                } else {
                    Log.d(TAG, "No existe el documento")
                }
            } else {
                Log.d(TAG, "Hubo fallo en ", task.exception)
            }
        })**/


        db.collection("users")
            .get()
            .addOnCompleteListener (OnCompleteListener<QuerySnapshot>{
                if(it.isSuccessful){
                    for (doc:DocumentSnapshot in it.result!!){
                        Log.d(TAG,"ESTA WEA NO CUENTA"+doc.id)
                       aux= cont++
                        Log.d(TAG,"VALOR DE CONTADOR EN LOAD INFO"+cont.toString())
                    }
                }else{
                    Log.d(TAG,"Error obteniendo documentos"+it.exception)
                }
            })
        return aux;
    }

    //TODO ESTA SE IMPLEMENTA EN onStart() para que funcione
    fun loadRealTimeInfo() {
        var mDocRef = db.document("users/persona_1")
        mDocRef.addSnapshotListener(this, EventListener<DocumentSnapshot> { snap, e ->
            if (e != null) {
                Log.e(TAG, "error", e)
            } else {
                var first = snap?.getString("first") + "\n" +
                        snap?.getString("last") + "\n" + snap?.get("born")
                var text_Test = findViewById<TextView>(R.id.text_Test1)
                text_Test.text = first
            }
        })
    }

    fun showOpcionesFragment(){
        val transaction =  supportFragmentManager.beginTransaction()
        val fragment = opcionesFragment()
        transaction.add(fragment,"FRAGMENT1")
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.menuforms, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.borrar -> Toast.makeText(this, "borrar", Toast.LENGTH_LONG).show()
                R.id.Logout -> {
                    mAuth.signOut()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
