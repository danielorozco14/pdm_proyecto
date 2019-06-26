package com.example.besafe.adapters.firestoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.besafe.R
import com.example.besafe.data.entities.Users
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class FirestoreUsersAdapter internal constructor(options: FirestoreRecyclerOptions<Users>):
    FirestoreRecyclerAdapter<Users, FirestoreUsersAdapter.UsersViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.cv_form_list_item,parent,false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(p0: UsersViewHolder, p1: Int, p2: Users) {
        p0.setUsersData(p2.first,p2.last,p2.born)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }


    inner class UsersViewHolder internal constructor(private val view: View):RecyclerView.ViewHolder(view){
        internal fun setUsersData(first:String,last:String,born:Int){
            val textView1= view.findViewById<TextView>(R.id.text_Test1)
            textView1.text=first
            val textView2= view.findViewById<TextView>(R.id.text_Test2)
            textView2.text=last
            val textView3= view.findViewById<TextView>(R.id.text_Test3)
            textView3.text=born.toString()
        }
    }
}

