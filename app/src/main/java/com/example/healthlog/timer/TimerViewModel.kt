package com.example.healthlog.timer

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TimerViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val exersiceAllTimer = ObservableField<String>()
    val showEditTimer = ObservableBoolean(true)
    val showSettingTimer = ObservableBoolean(false)

    val startStopWatchClick = View.OnClickListener {
        showEditTimer.set(false)
        showSettingTimer.set(true)
    }

    fun startTimer() {
        val source = TimerUtils.startTimer()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                exersiceAllTimer.set("${it}")
            }

        compositeDisposable.add(source)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}