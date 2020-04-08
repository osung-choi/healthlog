package com.example.healthlog.log

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmtest.utils.SingleLiveData

class LogViewModel: ViewModel() {

    private val _addExercise = SingleLiveData<String>()
    val addExercise: LiveData<String> = _addExercise

    private val _exerciseList = SingleLiveData<List<String>>()
    val exerciseList: LiveData<List<String>> = _exerciseList

    private var searchText = ""

    val addExerciseClick = View.OnClickListener {
        _addExercise.setValue(searchText)
    }

    val searchDone = {
        _addExercise.setValue(searchText)
    }

    fun onContentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        searchText = s.toString()
    }

    fun getExerciseList() {
        _exerciseList.setValue(arrayListOf("벤치프레스(가슴)", "덤벨프레스(가슴)", "클로즈그립벤치프레스(삼두)")) //추후 DB조회
    }
}

