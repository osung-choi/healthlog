package com.example.healthlog.timer

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

object TimerUtils {
    private var timerObserve : Observable<String>? = null

    fun startTimer() : Observable<String>{
        if(timerObserve == null) {
            timerObserve = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map { makeTimeFormat(it) }
        }

        return timerObserve!!
    }

    private fun makeTimeFormat(time: Long) : String{
        val hour = time/360
        val minute = (time%360)/60
        val second = (time%360)%60

        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
}