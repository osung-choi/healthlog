package com.example.healthlog.timer

import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.subjects.Subject

interface TimerImpl {
    fun getStopWatchSubject(): Subject<Pair<Int, Int>>
    fun getStopWatchCompleteSubject() : Subject<Pair<Int, Int>>
    fun getTimerObserver() : ConnectableObservable<String>

    fun setStopWatchTime(minute: Int, second: Int)
    fun startStopWatch()
    fun pauseStopWatch()
    fun restartStopWatch()
    fun endStopWatch()

}