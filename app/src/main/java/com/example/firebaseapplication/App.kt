package com.example.firebaseapplication

import android.app.Activity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class App : Activity() {
    companion object {
        val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}