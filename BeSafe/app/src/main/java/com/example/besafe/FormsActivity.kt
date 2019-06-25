package com.example.besafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.besafe.data.entities.FormQ
import com.example.besafe.data.entities.Question
import com.example.besafe.data.entities.Users
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_forms.*
import kotlinx.android.synthetic.main.cv_form_list_item.*


/**
 * YA FUNCIONA LA OBTENCION DE DATOS EN TIEMPO REAL, AUNQUE SOLO PARA UN DOCUMENTO ESPECIFICO DE LA COLECCION
 * PARA PROBARLO, LUEGO DE QUE INICIE LA APLICACION VAN A LA CONSOLO DE FIRESTORE Y CAMBIAN LOS VALORES DE EL DOCUMENTO "INFO"
 */
class FormsActivity : AppCompatActivity() {
    val TAG = "FIRESTORE"
    var db = FirebaseFirestore.getInstance()
    lateinit var Doc: String
    private var adapter: UsersFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forms)

        var mAuth = FirebaseAuth.getInstance()

        var recycler_view = findViewById<RecyclerView>(R.id.rv_forms)

        recycler_view.layoutManager = LinearLayoutManager(this)
        val query = db.collection("users").orderBy("first", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Users>().setQuery(query, Users::class.java).build()
        adapter = UsersFirestoreRecyclerAdapter(options)
        recycler_view.adapter = adapter

        var uidtoken = mAuth.currentUser?.uid.toString()

        val formQ = FormQ(uidtoken, 10)

        val arrayString: ArrayList<String> = ArrayList()
        val arrayNumber: ArrayList<Int> = ArrayList()
        val Question: Question

        for (i in 1..5) {
            arrayString.add(arrayString.size, i.toString())
            arrayNumber.add(arrayNumber.size, i)
        }

        Question =Question(arrayString, arrayNumber, 1, "ontas")

        db.collection("formq").add(formQ).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            db.collection("formq").document(documentReference.id).collection("question").add(Question)
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


        //addInfo()
        //loadInfo()

    }

    override fun onStart() {
        super.onStart()
        //loadRealTimeInfo()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

    fun addInfo() {
        val user = hashMapOf(
            "first" to "Leonardo",
            "last" to "Rostov",
            "born" to 1999
        )
        //SI SE USA ADD() FIRESTORE GENERA EL PROPIO ID DE EL DOCUMENTO, USANDO .DOCUMENT() Y SET() LO DEFINIMOS NOSOTROS
        //TODO TAL VEZ TENGA QUE REGRESAR ESTO A ADD() PARA QUE FUNCIONE EL RECYCLER VIEW

        db.collection("users")
            .document("persona_1")//ID DEL DOCUMENTO
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun loadInfo() {
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
        })
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


    private inner class UsersViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setUsersData(first: String, last: String, born: Int) {
            val textView1 = view.findViewById<TextView>(R.id.text_Test1)
            textView1.text = first
            val textView2 = view.findViewById<TextView>(R.id.text_Test2)
            textView2.text = last
            val textView3 = view.findViewById<TextView>(R.id.text_Test3)
            textView3.text = born.toString()
        }
    }

    private inner class UsersFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<Users>) :
        FirestoreRecyclerAdapter<Users, UsersViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_form_list_item, parent, false)
            return UsersViewHolder(view)
        }

        override fun onBindViewHolder(p0: UsersViewHolder, p1: Int, p2: Users) {
            p0.setUsersData(p2.first, p2.last, p2.born)
        }
    }


}
