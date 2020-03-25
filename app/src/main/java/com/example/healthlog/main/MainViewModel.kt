package com.example.healthlog.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmtest.utils.SingleLiveEvent
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

class MainViewModel : ViewModel() {
    //운동 시작 버튼 LiveData
    private val _startExercise = SingleLiveEvent<Boolean>()
    val startExcecise: LiveData<Boolean> = _startExercise

    //달력 날짜 선택 이벤트
    val dateSelectedListener = OnDateSelectedListener { widget, date, selected -> }

    //운동 시작 버튼 이벤트
    val startExerciseClick = View.OnClickListener {
        _startExercise.setValue(true)
    }
}