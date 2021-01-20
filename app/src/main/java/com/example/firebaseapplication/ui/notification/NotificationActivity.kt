package com.example.firebaseapplication.ui.notification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseapplication.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    companion object {
        fun newIntent(mContext: Context): Intent {
            return Intent(mContext, NotificationActivity::class.java)
        }
    }

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}