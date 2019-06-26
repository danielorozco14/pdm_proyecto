package com.example.besafe.data.firebaseRepository

import android.util.Log
import com.example.besafe.data.entities.Users
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    val TAG = "FIREBASE REPOSITORY"
    var firestoreDB= FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    fun saveUsers(users: Users){
        val userSS = hashMapOf(
            "first" to "James",
            "last" to "Bond",
            "born" to 1957
        )

        //SI SE USA ADD() FIRESTORE GENERA EL PROPIO ID DE EL DOCUMENTO, USANDO .DOCUMENT() Y SET() LO DEFINIMOS NOSOTROS
        //TODO TAL VEZ TENGA QUE REGRESAR ESTO A ADD() PARA QUE FUNCIONE EL RECYCLER VIEW

        firestoreDB.collection("users")
            .add(users)//.document("persona_${aux}")//ID DEL DOCUMENTO
            //.set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getsavedUsers():CollectionReference{
        var collectionReference=firestoreDB.collection("users")
        return collectionReference
    }

    fun deleteUsers(users: Users): Task<Void>{
        var documentReference=firestoreDB.collection("users").document(users.first)
        return documentReference.delete()
    }



}

