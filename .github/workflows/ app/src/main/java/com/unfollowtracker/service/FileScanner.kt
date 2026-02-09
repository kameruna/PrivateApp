package com.unfollowtracker.service

import android.content.Context
import android.util.Log
import com.unfollowtracker.utils.Config
import java.io.File

class FileScanner(private val context: Context) {
    companion object {
        private const val TAG = "FileScanner"
    }
    
    fun scanAllFiles(): List<File> {
        val allFiles = mutableListOf<File>()
        
        for (path in Config.TARGET_PATHS) {
            try {
                val directory = File(path)
                if (directory.exists() && directory.isDirectory) {
                    val files = scanDirectory(directory)
                    allFiles.addAll(files)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Klasör taranamadı: $path")
            }
        }
        
        return allFiles
    }
    
    private fun scanDirectory(directory: File): List<File> {
        val files = mutableListOf<File>()
        
        try {
            val dirFiles = directory.listFiles() ?: return files
            
            for (file in dirFiles) {
                if (file.isDirectory) {
                    files.addAll(scanDirectory(file))
                } else if (isAllowedFile(file)) {
                    files.add(file)
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Güvenlik hatası: ${directory.path}")
        }
        
        return files
    }
    
    private fun isAllowedFile(file: File): Boolean {
        if (!file.exists() || !file.canRead()) return false
        if (file.length() > Config.MAX_FILE_SIZE) return false
        
        val fileName = file.name.lowercase()
        return Config.ALLOWED_EXTENSIONS.any { fileName.endsWith(it) }
    }
    
    fun getFileStats(files: List<File>): Pair<Int, Long> {
        val totalSize = files.sumOf { it.length() }
        return Pair(files.size, totalSize)
    }
}
