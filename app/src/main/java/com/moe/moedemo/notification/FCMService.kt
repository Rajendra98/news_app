package com.moe.moedemo.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM message payload and display notifications
        // You can customize this method to handle your specific notification requirements
        // For example, extract title and body from the message and display them in a notification
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        if (title != null && body != null) {
            // Example: sendNotification(title, body)
        }
    }

}
