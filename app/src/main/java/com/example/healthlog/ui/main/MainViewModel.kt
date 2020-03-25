package com.example.healthlog.ui.main

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

class MainViewModel : ViewModel() {
    val TAG = MainViewModel::class.java.simpleName

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
    val dateSelectedListener = OnDateSelectedListener { widget, date, selected -> }
}