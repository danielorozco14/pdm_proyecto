package com.example.besafe.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.besafe.R
import com.example.besafe.adapters.firestoreAdapter.FirestoreUsersAdapter
import com.example.besafe.data.entities.Users
import com.example.besafe.data.viewModel.FirestoreViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class opcionesFragment : Fragment() {

    companion object {
        fun newInstance() :opcionesFragment = opcionesFragment()
    }

   private lateinit var Adapter :FirestoreUsersAdapter
    var db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.opciones_fragment, container, false)


       /** var recyclerView= view.findViewById<RecyclerView>(R.id.rv_fragmentOptions)
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val query = db.collection("users").orderBy("born", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Users>()
            .setQuery(query, Users::class.java)
            .build()
        //adapter = UsersFirestoreRecyclerAdapter(options)
        Adapter = FirestoreUsersAdapter(options)
        Adapter.notifyDataSetChanged()
        recyclerView.adapter=Adapter

        Log.d("FIRESTORE","SE CREO EL FRAGMENTO")**/
        Log.e("FIRESTORE","SE CREO EL FRAGMENTO")
        return view
    }



    override fun onStart() {
        super.onStart()
        //Adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
       /** if(Adapter != null) {
            Adapter.stopListening()
        }**/
    }

    //TODO QUIZAS HACER ESTO EN EL FORMSACTIVITY

    fun clickListeners(view : View){

        var fragment=fragmentManager
        var transaction= fragment?.beginTransaction()

        var opcion_1=view.findViewById<CardView>(R.id.opcion1)
        var opcion_2=view.findViewById<CardView>(R.id.opcion2)
        var opcion_3=view.findViewById<CardView>(R.id.opcion3)
        var opcion_4=view.findViewById<CardView>(R.id.opcion4)

        opcion_1.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = opcionesFragment()
            transaction.add(R.id.fragment_container, fragment)
            transaction.commit()

        }



    }


}
