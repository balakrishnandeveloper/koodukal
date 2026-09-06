package com.android.mykoodugalapplication.commonUtils

import android.app.Application
import android.content.Intent
import com.android.mykoodugalapplication.activity.MainActivity
import com.google.firebase.FirebaseApp
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import org.json.JSONObject

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        val ONESIGNAL_APP_ID = "c75a93ad-3fc0-4f21-a49a-d6b9e9ff8b5f"

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        OneSignal.setNotificationOpenedHandler { result: OSNotificationOpenedResult ->
            val data: JSONObject? = result.notification.additionalData

            val mood = data?.optString("Mood")
            val severity = data?.optString("Severity")
            val title = result.notification.title
            val message = result.notification.body

            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("title", title)
                putExtra("message", message)
                putExtra("mood", mood)
                putExtra("severity", severity)
            }
            startActivity(intent)
        }

    }
}

