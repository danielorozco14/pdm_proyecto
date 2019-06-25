package com.example.besafe.adapters.firestoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.besafe.R
import com.example.besafe.data.entities.Users
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.extensions.LayoutContainer

class FirestoreUsersAdapter(options: FirestoreRecyclerOptions<Users>):FirestoreRecyclerAdapter<Users,FirestoreUsersAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_form_list_item,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int, p2: Users) {
        p0.apply {
          //  text_Test1.text=p2.first

        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
