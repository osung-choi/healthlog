package com.example.healthlog.timer

import io.reactivex.rxjava3.subjects.Subject

interface TimerImpl {
    fun getStopWatchSubject(): Subject<Pair<Int, Int>>
    fun getStopWatchCompleteSubject() : Subject<Boolean>
    fun getTimerSubject() : Subject<String>
    fun getPauseStopWatchSubject() : Subject<Boolean>

    fun isFinishStopWatch(): Boolean
    fun startTimer()
    fun endTimer()
    fun setStopWatchTime(minute: Int, second: Int)
    fun startStopWatch()
    fun pauseStopWatch(isPause: Boolean)
    fun endStopWatch()

}