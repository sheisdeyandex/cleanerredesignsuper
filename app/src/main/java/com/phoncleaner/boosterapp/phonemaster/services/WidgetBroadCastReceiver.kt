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
import com.phoncleaner.boosterapp.phonemaster.AlarmReceiver
import com.phoncleaner.boosterapp.phonemaster.R
import com.phoncleaner.boosterapp.phonemaster.ui.MainActivity

class WidgetBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationLayout = RemoteViews("com.phoncleaner.boosterapp", R.layout.custom_notification_layout)
    val notificationIntent = Intent(context,MainActivity::class.java)
        notificationIntent.putExtra("whattodo","optimize")

     val notificationIntentEnergySaving = Intent(context,MainActivity::class.java)
        notificationIntentEnergySaving.putExtra("whattodo","energysaving")

     val notificationIntentCoolCpu = Intent(context,MainActivity::class.java)
        notificationIntentCoolCpu.putExtra("whattodo","coolcpu")

     val notificationIntentClean = Intent(context,MainActivity::class.java)
        notificationIntentClean.putExtra("whattodo","clean")

     val notificationIntentOthers = Intent(context,MainActivity::class.java)
        notificationIntentOthers.putExtra("whattodo","others")


//
    val pendingIntent = PendingIntent.getActivity(context,0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
    val pendingIntentEnergySaving = PendingIntent.getActivity(context,1, notificationIntentEnergySaving, PendingIntent.FLAG_IMMUTABLE)
    val pendingIntentCoolCpu =PendingIntent.getActivity(context,2, notificationIntentCoolCpu, PendingIntent.FLAG_IMMUTABLE)
    val pendingIntentClean = PendingIntent.getActivity(context,3, notificationIntentClean, PendingIntent.FLAG_IMMUTABLE)
    val pendingIntentOthers =PendingIntent.getActivity(context,4, notificationIntentOthers, PendingIntent.FLAG_IMMUTABLE)

    notificationLayout.setOnClickPendingIntent(R.id.iv_booster, pendingIntent)
    notificationLayout.setOnClickPendingIntent(R.id.iv_energy_saving, pendingIntentEnergySaving)
    notificationLayout.setOnClickPendingIntent(R.id.iv_coolcpu, pendingIntentCoolCpu)
    notificationLayout.setOnClickPendingIntent(R.id.iv_clean, pendingIntentClean)
    notificationLayout.setOnClickPendingIntent(R.id.iv_others, pendingIntentOthers)
        val customNotification = NotificationCompat.Builder(context, AlarmReceiver.CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setCustomContentView(notificationLayout)
            .setOngoing(true)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AlarmReceiver.CHANNEL_ID,
              context.resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
        context.startService(Intent(context, BatteryService::class.java))
        notificationManager.notify(0, customNotification)
    }

}