package com.example.healthlog.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
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

        val contentView = RemoteViews(packageName, R.layout.notification_stopwatch)
        //PendingIntent.getActivities(this, 0, )

        builder = if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SnowDeer Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(this)

        }.setSmallIcon(R.mipmap.ic_launcher)
            .setCustomContentView(contentView)
            .setContentIntent(pendingIntent)

        startForeground(1, builder.build())
    }

    private fun startExersice() {
        compositeDisposable.add(
            mTimerImpl.getTimerSubject().subscribe()
        )

        mTimerImpl.startTimer()

        compositeDisposable.add(
            mTimerImpl.getStopWatchSubject().subscribe {
                builder.setContentText(String.format("%02d:%02d",it.first, it.second))
                notificationManager.notify(1, builder.build())
            }
        )

        compositeDisposable.add(
            mTimerImpl.getStopWatchCompleteSubject().subscribe {

                val prefManager = PrefMananger()
                val minute = prefManager.getInt(this, PrefMananger.Key.PREF_STOPWATCH_MINUTE)
                val second = prefManager.getInt(this, PrefMananger.Key.PREF_STOPWATCH_SECOND)

                builder.setContentText("대기 중")
                notificationManager.notify(1, builder.build())
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()

        mTimerImpl.endTimer()
        mTimerImpl.endStopWatch()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}