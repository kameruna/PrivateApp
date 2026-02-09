package com.unfollowtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unfollowtracker.databinding.ActivityAddFollowersBinding

class AddFollowersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFollowersBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFollowersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnSave.setOnClickListener {
            // Takipçileri kaydetme işlevi (sahte)
            finish()
        }
        
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}
