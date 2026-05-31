package com.pesawaran.food.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pesawaran.food.R
import com.pesawaran.food.presentation.ui.customer.MainActivity
import com.pesawaran.food.utils.LogUtils
import timber.log.Timber

class PushNotificationService : FirebaseMessagingService() {

    private val TAG = "PushNotificationService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        LogUtils.d(TAG, "Message received")

        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: ""
            val body = remoteMessage.data["body"] ?: ""
            val type = remoteMessage.data["type"] ?: ""
            val orderId = remoteMessage.data["orderId"] ?: ""

            sendNotification(title, body, type, orderId)
        }

        remoteMessage.notification?.let {
            sendNotification(it.title ?: "", it.body ?: "", "", "")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        LogUtils.d(TAG, "New FCM token: $token")
        sendTokenToServer(token)
    }

    private fun sendNotification(
        title: String,
        body: String,
        type: String,
        orderId: String
    ) {
        val notificationId = System.currentTimeMillis().toInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("notificationType", type)
            putExtra("orderId", orderId)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(soundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun sendTokenToServer(token: String) {
        // Send token to server for later use
        LogUtils.d(TAG, "Token would be sent to server: $token")
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "pesawaran_food_channel"
        const val NOTIFICATION_CHANNEL_NAME = "Pesawaran Food Notifications"
    }
}