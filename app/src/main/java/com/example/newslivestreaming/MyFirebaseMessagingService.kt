package com.example.newslivestreaming
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM messages here
        // You can create and show notifications based on the message content
        // Example:
        remoteMessage.notification?.let {
            showNotification(it.title ?: "", it.body ?: "")
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun showNotification(title: String, body: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "news_7"
        val channelName = "com.example.newslivestreaming"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification_layout)
        notificationLayout.setTextViewText(R.id.tv_title, title)
        notificationLayout.setTextViewText(R.id.tv_description, body)

        val intent = Intent(this, NotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setCustomContentView(notificationLayout) // Set custom layout
            .setSmallIcon(R.drawable.notifications_icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        notificationManager.notify(10, notificationBuilder.build())
    }
}
