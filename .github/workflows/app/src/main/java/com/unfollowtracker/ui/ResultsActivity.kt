package com.unfollowtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unfollowtracker.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Sahte veriler
        binding.txtUnfollowers.text = "5 kişi takipten çıktı"
        binding.txtNewFollowers.text = "3 yeni takipçi"
        binding.txtSame.text = "42 değişmeyen"
    }
}
