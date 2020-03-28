package com.example.healthlog.timer

import android.view.View
import android.widget.NumberPicker
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TimerViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val exersiceAllTimer = ObservableField<String>()
    val showEditTimer = ObservableBoolean(true)
    val mPauseStopWatch = ObservableBoolean(true) //true : 중지, false : 계속

    val mStopWatchMinute = ObservableInt(1)
    val mStopWatchSecond = ObservableInt(30)

    //시작 버튼 클릭
    val startStopWatchClick = View.OnClickListener {
        val minute = mStopWatchMinute.get()
        val second = mStopWatchSecond.get()

        if(minute == 0 && second == 0) {
            return@OnClickListener
        }

        showEditTimer.set(false)

        val source = TimerUtils.startStopWatch(minute, second)
            .subscribe({
                mStopWatchMinute.set(it.first)
                mStopWatchSecond.set(it.second)
            }, {
                it.printStackTrace()
            }, {
                TimerUtils.endStopWatch()
                showEditTimer.set(true)

                mStopWatchMinute.set(minute)
                mStopWatchSecond.set(second)
            })

        compositeDisposable.add(source)
    }

    //중지 버튼 클릭
    val pauseStopWatchClick = View.OnClickListener {
        mPauseStopWatch.set(false)
        TimerUtils.pauseStopWatch()
    }

    //계속 버튼 클
    val restartStopWatchClick = View.OnClickListener {
        mPauseStopWatch.set(true)
        TimerUtils.restartStopWatch()
    }

    //분 NumberPicker 변경
    val changeStopWatchMinute = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
        mStopWatchMinute.set(newVal)
    }

    //초 NumberPicker 변경
    val changeStopWatchSecond = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
        mStopWatchSecond.set(newVal)
    }

    //초기화 버튼 클
    val clearStopWatchClick = View.OnClickListener {
        TimerUtils.endStopWatch()
    }

    fun startTimer() {
        val source = TimerUtils.startTimer()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                exersiceAllTimer.set(it)
            }

        compositeDisposable.add(source)
    }

    override fun onCleared() {
        super.onCleared()
        TimerUtils.endStopWatch()
        compositeDisposable.clear()
    }
}