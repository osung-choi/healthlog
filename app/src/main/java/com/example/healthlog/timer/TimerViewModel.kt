package com.example.healthlog.timer

import android.util.Log
import android.view.View
import android.widget.NumberPicker
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class TimerViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
        startTimer()
    }

    private val _mMinimalWindowStatus = MutableLiveData<Boolean>() //최소화 여부
    val mMinimalWindowStatus : LiveData<Boolean> = _mMinimalWindowStatus

    val exersiceAllTimer = ObservableField<String>()
    val showEditTimer = ObservableBoolean(true)
    val mPauseStopWatch = ObservableBoolean(true) //true : 중지, false : 계속

    val mStopWatchMinute = ObservableInt(1)
    val mStopWatchSecond = ObservableInt(30)

    private var lastMinute = 0
    private var lastSecond = 0

    //시작 버튼 클릭
    val startStopWatchClick = View.OnClickListener {
        lastMinute = mStopWatchMinute.get()
        lastSecond = mStopWatchSecond.get()

        if(lastMinute == 0 && lastSecond == 0) {
            return@OnClickListener
        }

        TimerUtils.setStopWatch(lastMinute, lastSecond)
        val source = TimerUtils.startStopWatch()
        val disposable = source?.subscribe({
                mStopWatchMinute.set(it.first)
                mStopWatchSecond.set(it.second)
            }, {
                it.printStackTrace()
            }, {
                TimerUtils.endStopWatch()
            })

        if(source != null) {
            showEditTimer.set(false)
            compositeDisposable.add(disposable)
        }
    }

    //중지 버튼 클릭
    val pauseStopWatchClick = View.OnClickListener {
        mPauseStopWatch.set(false)
        TimerUtils.pauseStopWatch()
    }

    //계속 버튼 클릭
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

        showEditTimer.set(true)

        mStopWatchMinute.set(lastMinute)
        mStopWatchSecond.set(lastSecond)

        TimerUtils.endStopWatch()
    }

    //최소화 클릭
    val mMinWindowClick = View.OnClickListener {
        _mMinimalWindowStatus.value = true
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