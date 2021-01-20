package com.example.firebaseapplication.ui.users

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseapplication.databinding.ActivityUsersBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class UsersActivity : AppCompatActivity() {
    companion object {
        fun newIntent(mContext: Context): Intent {
            return Intent(mContext, UsersActivity::class.java)
        }
    }

    private lateinit var binding: ActivityUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uiInit()
    }

    private fun uiInit() {
        with(binding) {
            val getData = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val sb = StringBuilder()
                    for (i in snapshot.children) {
                        val name = i.child("name").value
                        val address = i.child("address").value
                        val contact = i.child("contact").value
                        sb.append("$name $address $contact\n\n")
                    }
                    tvUsers.text = sb
                }

                override fun onCancelled(error: DatabaseError) {}
            }
            val database = FirebaseDatabase.getInstance().reference
            database.addValueEventListener(getData)
            database.addListenerForSingleValueEvent(getData)
        }
    }
}