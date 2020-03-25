package com.example.healthlog.ui.utils

import android.util.Log
import android.widget.CalendarView
import androidx.databinding.BindingAdapter
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setOnDateSelectedListener")
    fun setOnDateSelectedListener(calendar: MaterialCalendarView, onDayChange: OnDateSelectedListener?) {
        calendar.setOnDateChangedListener(onDayChange)
        Log.d("zxc","asdzcs")
    }
}