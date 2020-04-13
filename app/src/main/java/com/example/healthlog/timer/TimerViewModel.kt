package com.example.healthlog.timer

import android.util.Log
import android.view.View
import android.widget.NumberPicker
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.healthlog.model.TimerUtils
import com.example.mvvmtest.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable

class TimerViewModel: ViewModel() {
    private val _mMinimalWindowStatus = SingleLiveData<Boolean>() //최소화 여부
    val mMinimalWindowStatus : LiveData<Boolean> = _mMinimalWindowStatus

    private val _mStartStopWatch = SingleLiveData<Pair<Int, Int>>()
    val mStartStopWatch : LiveData<Pair<Int, Int>> = _mStartStopWatch

    val exersiceAllTimer = ObservableField<String>()
    val showEditTimer = ObservableBoolean(true)
    val mPauseStopWatch = ObservableBoolean(true) //true : 중지, false : 계속

    val mStopWatchMinute = ObservableInt(0)
    val mStopWatchSecond = ObservableInt(0)

    var lastMinute = 0
    var lastSecond = 0

    private val compositeDisposable = CompositeDisposable()

    private val timerImpl = TimerUtils.Instance.getInstance()

    init {
        compositeDisposable.add(
            timerImpl.getTimerSubject()
                .subscribe { exersiceAllTimer.set(it) }
        )

        compositeDisposable.add(
            timerImpl.getStopWatchSubject()
                .filter { !timerImpl.isFinishStopWatch() }
                .subscribe{
                mPauseStopWatch.set(true)
                showEditTimer.set(false)

                mStopWatchMinute.set(it.first)
                mStopWatchSecond.set(it.second)
            }
        )

        compositeDisposable.add(
            timerImpl.getStopWatchCompleteSubject()
                .subscribe {
                showEditTimer.set(true)

                mStopWatchMinute.set(lastMinute)
                mStopWatchSecond.set(lastSecond)

                Log.d("asd", "$it, $lastMinute, $lastSecond")
            }
        )

        compositeDisposable.add(
            timerImpl.getPauseStopWatchSubject().subscribe {
                mPauseStopWatch.set(!it)
            }
        )
    }

    //시작 버튼 클릭
    val startStopWatchClick = View.OnClickListener {
        lastMinute = mStopWatchMinute.get()
        lastSecond = mStopWatchSecond.get()

        _mStartStopWatch.setValue(Pair(lastMinute, lastSecond)) //마지막 시간 저장

        timerImpl.setStopWatchTime(lastMinute, lastSecond)
        timerImpl.startStopWatch()

        showEditTimer.set(false)
        mPauseStopWatch.set(true)
    }

    fun initViewModel(minute: Int, second: Int) {
        if(timerImpl.isFinishStopWatch()) {
            mStopWatchMinute.set(minute)
            mStopWatchSecond.set(second)

            lastMinute = minute
            lastSecond = second
        }
    }

    //중지 버튼 클릭
    val pauseStopWatchClick = View.OnClickListener {
        mPauseStopWatch.set(false)
        timerImpl.pauseStopWatch(true)
    }

    //계속 버튼 클릭
    val restartStopWatchClick = View.OnClickListener {
        mPauseStopWatch.set(true)
        timerImpl.startStopWatch()
    }

    //분 NumberPicker 변경
    val changeStopWatchMinute = NumberPicker.OnValueChangeListener { _, _, newVal ->
        mStopWatchMinute.set(newVal)
    }

    //초 NumberPicker 변경
    val changeStopWatchSecond = NumberPicker.OnValueChangeListener { _, _, newVal ->
        mStopWatchSecond.set(newVal)
    }

    //초기화 버튼 클
    val clearStopWatchClick = View.OnClickListener {
        timerImpl.endStopWatch()
    }

    //최소화 클릭
    val mMinWindowClick = View.OnClickListener {
        _mMinimalWindowStatus.setValue(true)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

}