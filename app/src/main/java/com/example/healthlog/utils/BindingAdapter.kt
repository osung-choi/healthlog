package com.example.healthlog.utils

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setOnDateSelectedListener")
    fun setOnDateSelectedListener(calendar: MaterialCalendarView, onDayChange: OnDateSelectedListener?) {
        calendar.setOnDateChangedListener(onDayChange)
        Log.d("zxc","asdzcs")
    }

    @JvmStatic
    @BindingAdapter("setVisibility")
    fun setVisibility(view: View, isShow: Boolean) {
        if(isShow)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.INVISIBLE
    }
}