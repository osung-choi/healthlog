package com.example.healthlog.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.TimeUtils
import androidx.core.app.NotificationCompat
import com.example.healthlog.MainActivity
import com.example.healthlog.R
import java.util.*


class TimerService: Service() {
    val CHANNEL_ID = "TimerService"

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()

        startForegroundService()

        stopWatch()
    }

    fun startForegroundService() {

        val notificationIntent = Intent(this, TimerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        //val remoteViews = RemoteViews(packageName, R.layout.notification_service)

        notificationManager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SnowDeer Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            builder = NotificationCompat.Builder(this)
        }
        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("asd")
            .setContentIntent(pendingIntent)
        startForeground(1, builder.build())
    }

    private fun stopWatch() {
        TimerUtils.setStopWatch(1,30)
        val source = TimerUtils.startStopWatch()
        source?.subscribe {
            builder.setContentText("${it.first}:${it.second}")
            notificationManager.notify(1, builder.build())
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}