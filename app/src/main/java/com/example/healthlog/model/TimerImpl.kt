package com.example.healthlog.model

import io.reactivex.rxjava3.subjects.Subject

interface TimerImpl {
    fun getStopWatchSubject(): Subject<Pair<Int, Int>>
    fun getStopWatchCompleteSubject() : Subject<Boolean>
    fun getTimerSubject() : Subject<String>
    fun getPauseStopWatchSubject() : Subject<Boolean>

    fun isFinishStopWatch(): Boolean //이 함수가 잘못됬음... 일단 넘어가자
    fun startTimer()
    fun endTimer()
    fun setStopWatchTime(minute: Int, second: Int)
    fun startStopWatch()
    fun pauseStopWatch(isPause: Boolean)
    fun endStopWatch()

}