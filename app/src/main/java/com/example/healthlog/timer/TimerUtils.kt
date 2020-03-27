package com.example.healthlog.timer

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.processors.PublishProcessor
import java.util.concurrent.TimeUnit


object TimerUtils {
    private var timerObserve : Observable<String>? = null

    fun startTimer() : Observable<String>{
        if(timerObserve == null) {
            timerObserve = Observable.interval(0, 1, TimeUnit.MILLISECONDS)
                .map { makeTimeFormat(it) }
        }

        return timerObserve!!
    }

    private fun makeTimeFormat(time: Long) : String{
         val hour = time / (1000 * 60 * 60)
        val minute = time / (1000 * 60)
        val second = time / (1000)
        val ms = (time%1000) / 10 //두 자리만 표현

        return String.format("%02d:%02d:%02d.%02d", hour, minute, second, ms)
    }
}