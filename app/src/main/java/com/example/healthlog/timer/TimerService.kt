package com.example.healthlog.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.healthlog.R
import com.example.healthlog.utils.PrefMananger
import io.reactivex.rxjava3.disposables.CompositeDisposable


class TimerService: Service() {
    val CHANNEL_ID = "TimerService"

    lateinit var mTimerImpl: TimerImpl

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()

        mTimerImpl = TimerUtils.Instance.getInstance()

        startForegroundService()
        startExersice()
    }

    fun startForegroundService() {

        val notificationIntent = Intent(this, TimerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

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
            .setContentIntent(pendingIntent)
        startForeground(1, builder.build())
    }

    private fun startExersice() {
        val source = mTimerImpl.getTimerObserver()
        source.subscribe()
            //builder.setContentTitle(it)
            //notificationManager.notify(1, builder.build())
        compositeDisposable.add(
            source.connect()
        )

        compositeDisposable.add(
            mTimerImpl.getStopWatchSubject().subscribe {
                builder.setContentText(String.format("%02d:%02d",it.first, it.second))
                notificationManager.notify(1, builder.build())
            }
        )

        compositeDisposable.add(
            mTimerImpl.getStopWatchCompleteSubject().subscribe {
                PrefMananger().setBoolean(this, PrefMananger.Key.PREF_TIMER_RUNNING, false)

                builder.setContentText("대기 중")
                notificationManager.notify(1, builder.build())
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        PrefMananger().setBoolean(this, PrefMananger.Key.PREF_TIMER_RUNNING, false)

        mTimerImpl.endStopWatch()

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}