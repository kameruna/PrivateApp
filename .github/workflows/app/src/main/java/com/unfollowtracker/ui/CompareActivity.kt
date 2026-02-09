package com.unfollowtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unfollowtracker.databinding.ActivityCompareBinding

class CompareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompareBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnCompare.setOnClickListener {
            // Karşılaştırma işlevi (sahte)
            binding.txtResult.text = "Karşılaştırma tamamlandı!"
        }
    }
}
