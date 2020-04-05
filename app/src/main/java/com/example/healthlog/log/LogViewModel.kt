package com.example.healthlog.log

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogViewModel: ViewModel() {
    val exerciseList = arrayListOf("벤치프레스(가슴)", "덤벨프레스(가슴)", "클로즈그립벤치프레스(삼두)") //추후 DB저장 형태 변경(ExerciseList)
    val list = arrayListOf<String>()
    private val _searchTextChange = MutableLiveData<List<String>>()
    val searchTextChange: LiveData<List<String>> = _searchTextChange

    fun onContentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        list.clear()

        exerciseList.forEach {
            if(it.contains(s.toString()))
                list.add(it)
        }

        _searchTextChange.value = list
    }
}

