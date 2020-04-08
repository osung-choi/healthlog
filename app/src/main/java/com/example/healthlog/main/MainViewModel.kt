package com.example.healthlog.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthlog.model.DateUtils
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

class MainViewModel : ViewModel() {
    //운동 시작 버튼 LiveData
    private val _startExercise = MutableLiveData<Boolean>()
    val startExcecise: LiveData<Boolean> = _startExercise

    private val _editExerciseLog = MutableLiveData<String>()
    val editExerciseLog: LiveData<String> = _editExerciseLog

    //달력 날짜 선택 이벤트
    val dateSelectedListener = OnDateSelectedListener { _, date, _ ->
        DateUtils().isBeforeDate(date.date)
            .subscribe {
                _editExerciseLog.value = it
            }
    }

    //운동 시작 버튼 이벤트
    val startExerciseClick = View.OnClickListener {
        _startExercise.value = true
    }
}