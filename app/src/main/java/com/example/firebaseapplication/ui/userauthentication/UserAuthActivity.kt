package com.example.firebaseapplication.ui.userauthentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseapplication.R

class UserAuthActivity : AppCompatActivity() {
    companion object {
        fun newIntent(mContext: Context): Intent {
            return Intent(mContext, UserAuthActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_auth)
    }
}