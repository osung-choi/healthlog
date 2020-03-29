package com.example.healthlog.timer


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong


object TimerUtils {
    private val lastTick: AtomicLong = AtomicLong(0L)
    private var pauseFlag = false
    private var clearFlag = false

    private var minute = 0
    private var second = 0

    private var stopWatchSubject : PublishSubject<Pair<Int, Int>>? = null

    fun startTimer() : Observable<String> {
        return Observable.interval(0, 1000, TimeUnit.MICROSECONDS)
            .map { makeTimeFormat(it) }
    }

    fun setStopWatch(minute: Int, second: Int) {
        this.minute = minute
        this.second = second
    }

    fun startStopWatch() : PublishSubject<Pair<Int,Int>>? {
        if(minute == 0 && second == 0) return null

        if(stopWatchSubject == null) {
            clearFlag = false

            stopWatchSubject = PublishSubject.create()

            Observable.interval(0, 1, TimeUnit.SECONDS)
                .filter { !pauseFlag }
                .map { lastTick.getAndIncrement() }
                .map { ((minute * 60) + second) - it }
                .takeUntil { it < 1 || clearFlag}
                .map { getMinuteAndSecond(it) }
                .subscribe( {
                    stopWatchSubject!!.onNext(it)
                }, {
                    stopWatchSubject!!.onError(it)
                    stopWatchSubject = null
                }, {
                    stopWatchSubject!!.onComplete()
                    stopWatchSubject = null
                })
        }

        else if(stopWatchSubject != null && clearFlag){
            return null
        }

        return stopWatchSubject
    }

    fun pauseStopWatch() {
        pauseFlag = true
    }

    fun restartStopWatch() {
        pauseFlag = false
    }

    fun endStopWatch() {
        lastTick.set(0L)
        pauseFlag = false
        clearFlag = true
    }

    private fun makeTimeFormat(time: Long) : String {
        return SimpleDateFormat("HH:mm:ss.SS").let {
            it.timeZone = TimeZone.getTimeZone("GMT")
            it.format(Date(time))
        }
    }

    private fun getMinuteAndSecond(time: Long) : Pair<Int,Int> {
        val minute = time / 60
        val second = time % 60
        return Pair(minute.toInt(), second.toInt())
    }

//    private fun getMinuteAndSecond(time: Long) : Pair<Int,Int> {
//        return SimpleDateFormat("mm:ss").run {
//            this.timeZone = TimeZone.getTimeZone("GMT")
//            val split = this.format(Date(time)).split(":")
//            Pair(split[0].toInt(), split[1].toInt())
//        }
//    }
}