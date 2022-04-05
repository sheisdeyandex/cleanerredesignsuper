package com.phoncleaner.boosterapp.phonemaster.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.ui.MainActivity

class AppInstalledReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, p1: Intent?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationLayout = RemoteViews("com.phoncleaner.boosterapp", R.layout.appinstalled_notification_layout)
        val notificationIntent = Intent(context,MainActivity::class.java)
        notificationIntent.putExtra("whattodo","clean")
        val pendingIntent = PendingIntent.getActivity(context,0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        notificationLayout.setOnClickPendingIntent(R.id.btn_clean, pendingIntent)
        val customNotification = NotificationCompat.Builder(context, "appdeleted")
            .setSmallIcon(R.drawable.ic_clean_ram)
            .setCustomContentView(notificationLayout)
            .setAutoCancel(true)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "appdeleted",
                context.resources.getString(R.string.app_name)+"appdeleted",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, customNotification)
        }

}