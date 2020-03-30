package com.example.healthlog.timer


import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong


class TimerUtils : TimerImpl {
    object Instance {
        private val timerUtils = TimerUtils()
        fun getInstance() : TimerImpl {
            return timerUtils
        }
    }

    private constructor()

    private val lastTick: AtomicLong = AtomicLong(0L)
    private var playStopWatch = true
    private var finishStopWatch = false

    private var minute = 0
    private var second = 0

    private val stopWatchSubject: BehaviorSubject<Pair<Int, Int>> = BehaviorSubject.create()

    private val stopWatchComplete: PublishSubject<Pair<Int, Int>> = PublishSubject.create()


    private val timerObservable: ConnectableObservable<String> = Observable.interval(0, 1, TimeUnit.SECONDS)
        .map {
            Log.d("asd", makeTimeFormat(it))
            makeTimeFormat(it) }
        .publish()

    private var stopWatchObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
        .filter { playStopWatch }
        .map { lastTick.getAndIncrement() }
        .map { ((minute * 60) + second) - it }
        .takeUntil { it < 1 || finishStopWatch}
        .map { getMinuteAndSecond(it) }

    private var stopWatchDisposable: Disposable? = null

    override fun getStopWatchSubject(): Subject<Pair<Int, Int>> {
        return stopWatchSubject
    }

    override fun getStopWatchCompleteSubject(): Subject<Pair<Int, Int>> {
        return stopWatchComplete
    }

    override fun getTimerObserver(): ConnectableObservable<String> {
        return timerObservable
    }

    override fun setStopWatchTime(minute: Int, second: Int) {
        this.minute = minute
        this.second = second
    }

    override fun pauseStopWatch() {
        playStopWatch = false
    }

    override fun restartStopWatch() {
        playStopWatch = true
    }

    override fun startStopWatch() {
        if(stopWatchDisposable == null || stopWatchDisposable!!.isDisposed) {
            finishStopWatch = false

            stopWatchDisposable = stopWatchObservable.subscribe( {
                Log.d("asd","startStopWatch onNext ${it}")

                stopWatchSubject.onNext(it)
            }, {}, {
                Log.d("asd","startStopWatch onComplete")
                lastTick.set(0L)

                stopWatchComplete.onNext(Pair(minute, second))
            })
        }
    }

    override fun endStopWatch() {
        finishStopWatch = true
    }

    private fun makeTimeFormat(time: Long) : String {
        val milliTime = time * 1000

        return SimpleDateFormat("HH:mm:ss").let {
            it.timeZone = TimeZone.getTimeZone("GMT")
            it.format(Date(milliTime))
        }
    }

    private fun getMinuteAndSecond(time: Long) : Pair<Int,Int> {
        val milliTime = time * 1000

        return SimpleDateFormat("mm:ss").run {
            this.timeZone = TimeZone.getTimeZone("GMT")
            val split = this.format(Date(milliTime)).split(":")
            Pair(split[0].toInt(), split[1].toInt())
        }
    }
}