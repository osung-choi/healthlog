package com.example.healthlog.timer

import com.example.healthlog.timer.TimerUtils.lastTick
import io.reactivex.rxjava3.core.Observable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong


object TimerUtils {
    private val lastTick: AtomicLong = AtomicLong(0L)
    private var pauseFlag = false
    private var clearFlag = false

    fun startTimer() : Observable<String> {
        return Observable.interval(5, 1, TimeUnit.MILLISECONDS)
            .map { makeTimeFormat(it) }
    }

    fun startStopWatch(minute: Int, second: Int) : Observable<Pair<Int,Int>> {
        clearFlag = false

        return Observable.interval(5, 1, TimeUnit.MILLISECONDS)
            .filter { !pauseFlag }
            .map { lastTick.getAndIncrement() }
            .map { ((minute * 60 * 1000) + second * 1000) - it }
            .takeUntil { it < 1 || clearFlag}
            .map { getMinuteAndSecond(it) }
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
        return SimpleDateFormat("mm:ss").run {
            this.timeZone = TimeZone.getTimeZone("GMT")
            val split = this.format(Date(time)).split(":")
            Pair(split[0].toInt(), split[1].toInt())
        }
    }
}