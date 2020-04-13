package com.example.healthlog.timer

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.healthlog.R
import com.example.healthlog.model.TimerImpl
import com.example.healthlog.model.TimerUtils
import com.example.healthlog.utils.PrefMananger
import io.reactivex.disposables.CompositeDisposable


class TimerService: Service() {
    val ACTION_TOGGLE_PLAY = "com.example.healthlog.ACTION_TOGGLE_PLAY"
    val ACTION_STOP = "com.example.healthlog.ACTION_STOP"
    
    private val CHANNEL_ID = "TimerService"

    lateinit var mTimerImpl: TimerImpl

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder
    lateinit var remoteViews : RemoteViews

    var isStopWatchPlaying = false

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()

        mTimerImpl = TimerUtils.Instance.getInstance()

        val intentfilter = IntentFilter()
        intentfilter.addAction(ACTION_TOGGLE_PLAY)
        intentfilter.addAction(ACTION_STOP)

        registerReceiver(mReceiver, intentfilter)

        startForegroundService()
        startExersice()
    }

    fun startForegroundService() {
        val notificationIntent = Intent(this, TimerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        notificationManager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        createRemoteView(R.layout.notification_stopwatch)

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
            .setCustomContentView(remoteViews)
            .setContentIntent(pendingIntent)

        startForeground(1, builder.build())
    }

    fun createRemoteView(layoutId : Int) {
        remoteViews = RemoteViews(packageName, layoutId)
        val actionTogglePlay = Intent(ACTION_TOGGLE_PLAY)
        val actionStop = Intent(ACTION_STOP)

        val togglePlay = PendingIntent.getBroadcast(this, 0, actionTogglePlay, 0)
        val stop = PendingIntent.getBroadcast(this, 0, actionStop, 0)

        remoteViews.setOnClickPendingIntent(R.id.btnNotiPlay, togglePlay)
        remoteViews.setOnClickPendingIntent(R.id.btnNotiStop, stop)
    }

    private fun updatePlayButton() {
        if(isStopWatchPlaying) {
            remoteViews.setImageViewResource(R.id.btnNotiPlay, R.drawable.icon_pause)
        }else {
            remoteViews.setImageViewResource(R.id.btnNotiPlay, R.drawable.icon_play)
        }

        notificationManager.notify(1, builder.build())
    }

    private fun updateStopWatchTime(minute: Int, second: Int) {
        remoteViews.setTextViewText(R.id.tvStopWatchTime, String.format("%02d:%02d",minute, second))
        notificationManager.notify(1, builder.build())
    }

    private fun startExersice() {
        compositeDisposable.add(
            mTimerImpl.getTimerSubject().subscribe()
        )

        mTimerImpl.startTimer()

        compositeDisposable.add(
            mTimerImpl.getStopWatchSubject()
                .filter { !mTimerImpl.isFinishStopWatch() }
                .subscribe ({
                isStopWatchPlaying = true

                updatePlayButton()
                updateStopWatchTime(it.first, it.second)
            }, { }, {
                stopWatchComplete()
            })
        )

        compositeDisposable.add(
            mTimerImpl.getStopWatchCompleteSubject()
                .filter { it }
                .subscribe {
                stopWatchComplete()
            }
        )

        compositeDisposable.add(
            mTimerImpl.getPauseStopWatchSubject().subscribe {
                isStopWatchPlaying = false

                updatePlayButton()
            }
        )

        if(mTimerImpl.isFinishStopWatch()) {
            setLastStopWatch()
        }
    }

    private fun stopWatchComplete() {
        isStopWatchPlaying = false
        updatePlayButton()
        setLastStopWatch()
    }

    private fun setLastStopWatch() {
        val prefManager = PrefMananger()
        val minute = prefManager.getInt(this, PrefMananger.Key.PREF_STOPWATCH_MINUTE)
        val second = prefManager.getInt(this, PrefMananger.Key.PREF_STOPWATCH_SECOND)

        updateStopWatchTime(minute, second)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()

        mTimerImpl.endTimer()
        mTimerImpl.endStopWatch()

        unregisterReceiver(mReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    val mReceiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when(p1!!.action) {
                ACTION_TOGGLE_PLAY -> {
                    Log.d("TAG","ACTION_TOGGLE_PLAY")

                    if(isStopWatchPlaying) {
                        isStopWatchPlaying = false

                        mTimerImpl.pauseStopWatch(true)
                    }else {
                        isStopWatchPlaying = true

                        mTimerImpl.startStopWatch()
                    }

                    updatePlayButton()
                }

                ACTION_STOP -> {
                    Log.d("TAG","ACTION_STOP")

                    mTimerImpl.endStopWatch()
                }
            }
        }

    }
}