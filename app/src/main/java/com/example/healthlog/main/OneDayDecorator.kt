package com.example.healthlog.main

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class OneDayDecorator : DayViewDecorator{
    private val date: CalendarDay = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day!! == date
    }

    override fun decorate(view: DayViewFacade?) {
        view!!.addSpan(StyleSpan(Typeface.BOLD));
        view.addSpan(RelativeSizeSpan(1.4f));
        view.addSpan(ForegroundColorSpan(Color.GREEN));
    }

}