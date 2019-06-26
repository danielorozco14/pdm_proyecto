package com.example.besafe.data.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besafe.data.entities.Users
import com.example.besafe.data.firebaseRepository.FirebaseRepository
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class FirestoreViewModel:ViewModel() {
    val TAG="FIRESTORE_VIEW_MODEL"
    var firebaseRepository= FirebaseRepository()
    var savedUsers:MutableLiveData<List<Users>> = MutableLiveData()

    fun saveUserToFirebase(users: Users){
        firebaseRepository.saveUsers(users)
    }

    fun getSavedUsers():LiveData<List<Users>>{
        firebaseRepository.getsavedUsers().addSnapshotListener(EventListener<QuerySnapshot>{value,e->
            if(e!=null){
                savedUsers.value=null
                return@EventListener
            }
            var savedUsersList:MutableList<Users> = mutableListOf()
            for(doc in value!!){
                var user=doc.toObject(Users::class.java)
                savedUsersList.add(user)
            }
            savedUsers.value=savedUsersList
        })
        return savedUsers
    }


    fun deleteAddress(user: Users){
        firebaseRepository.deleteUsers(user).addOnFailureListener {
            Log.e(TAG,"Failed to delete Address")
        }
    }

}