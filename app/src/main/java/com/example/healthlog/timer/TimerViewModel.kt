package com.example.healthlog.timer

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TimerViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val exersiceTimer = ObservableField<String>()

    fun startTimer() {
        val source = TimerUtils.startTimer()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                exersiceTimer.set("운동 시간 => ${it}")
            }

        compositeDisposable.add(source)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}