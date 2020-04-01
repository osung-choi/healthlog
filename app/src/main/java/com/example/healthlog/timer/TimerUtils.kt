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

    private var stopWatchSubject: BehaviorSubject<Pair<Int, Int>> = BehaviorSubject.create()
    private var stopWatchComplete: PublishSubject<Boolean> = PublishSubject.create()
    private var timerSubject: BehaviorSubject<String> = BehaviorSubject.create()
    private var pauseStopWatchSubject: PublishSubject<Boolean> = PublishSubject.create()

    private val timerObserver = Observable.interval(0, 1, TimeUnit.SECONDS)
        .takeUntil{ finishTimer }
        .map { makeTimeFormat(it) }

    private var stopWatchObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
        .filter { playStopWatch }
        .map { lastTick.getAndIncrement() }
        .map { ((minute * 60) + second) - it }
        .takeUntil { it < 1 || finishStopWatch}
        .map { getMinuteAndSecond(it) }

    private val lastTick: AtomicLong = AtomicLong(0L)
    private var playStopWatch = true
    private var finishStopWatch = false
    private var finishTimer = false

    private var minute = 0
    private var second = 0

    private var stopWatchDisposable: Disposable? = null

    override fun clearSubject() {
        stopWatchSubject.onComplete()
        stopWatchComplete.onComplete()
        timerSubject.onComplete()
        pauseStopWatchSubject.onComplete()


        stopWatchSubject = BehaviorSubject.create()
        stopWatchComplete = PublishSubject.create()
        timerSubject =  BehaviorSubject.create()
        pauseStopWatchSubject = PublishSubject.create()
    }

    override fun getStopWatchSubject(): Subject<Pair<Int, Int>> {
        return stopWatchSubject
    }

    override fun getStopWatchCompleteSubject(): Subject<Boolean> {
        return stopWatchComplete
    }

    override fun getTimerSubject(): Subject<String> {
        return timerSubject
    }

    override fun getPauseStopWatchSubject(): Subject<Boolean> {
        return pauseStopWatchSubject
    }

    override fun startTimer() {
        finishTimer = false
        timerObserver.subscribe {
            timerSubject.onNext(it)
        }
    }

    override fun endTimer() {
        finishTimer = true
    }

    override fun setStopWatchTime(minute: Int, second: Int) {
        this.minute = minute
        this.second = second
    }

    override fun pauseStopWatch() {
        playStopWatch = false

        pauseStopWatchSubject.onNext(true)
    }

    override fun startStopWatch() {
        if(!playStopWatch) {
            playStopWatch = true

        } else if(stopWatchDisposable == null || stopWatchDisposable!!.isDisposed) {
            finishStopWatch = false

            stopWatchDisposable = stopWatchObservable.subscribe( { stopWatchSubject.onNext(it) }, {}, {
                lastTick.set(0L)
                stopWatchComplete.onNext(true)
            })
        }
    }

    override fun endStopWatch() {
        playStopWatch = true
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