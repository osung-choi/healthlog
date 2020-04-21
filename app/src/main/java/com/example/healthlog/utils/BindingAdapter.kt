package com.example.healthlog.utils

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.R
import com.example.healthlog.model.database.entitiy.ExerciseLog
import com.example.healthlog.adapter.LogAdapter
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setOnDateSelectedListener")
    fun setOnDateSelectedListener(calendar: MaterialCalendarView, onDayChange: OnDateSelectedListener?) {
        calendar.setOnDateChangedListener(onDayChange)
    }

    @JvmStatic
    @BindingAdapter("setVisibility")
    fun setVisibility(view: View, isShow: Boolean) {
        if(isShow)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("setInitNumberPicker")
    fun setInitNumberPicker(view: NumberPicker, value: Int) {
        view.value = value
    }

    @JvmStatic
    @BindingAdapter("setOnValueChangeListener")
    fun setOnValueChangeListener(view: NumberPicker, listener: NumberPicker.OnValueChangeListener) {
        view.setOnValueChangedListener(listener)
    }

    /**
     * 현재는 토글버튼으로 BindingAdapter를 활용하여 구현하였다.
     * 나중에 CustomView로 구현 해 볼것.
     */
    @JvmStatic
    @BindingAdapter("changeStopWatchButton", "pauseStopWatchListener", "restartStopWatchListener")
    fun changeStopWatchButton(view: Button,
                              flag: Boolean,
                              pauseListener: View.OnClickListener,
                              restartListener: View.OnClickListener) {
        val context = view.context

        if(flag) {
            view.text = context.getString(R.string.btn_pause)
            view.background = context.getDrawable(R.drawable.round_red_button)
            view.setOnClickListener(pauseListener)
        }else {
            view.text = context.getString(R.string.btn_restart)
            view.background = context.getDrawable(R.drawable.round_primary_button)
            view.setOnClickListener(restartListener)
        }
    }

    @JvmStatic
    @BindingAdapter("setIntToTime")
    fun setIntToTime(view: TextView, value: Int) {
        view.text = String.format("%02d", value)
    }

    @JvmStatic
    @BindingAdapter("onEditorActionDone")
    fun setOnEditorActionDone(view: TextView, listener: (() -> Unit)?) {
        listener?.let {
            view.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    it()
                }

                false
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setExerciseItem")
    fun setExerciseItem(recyclerView: RecyclerView, list: LiveData<ArrayList<ExerciseLog>>) {
        list.value?.let {
            (recyclerView.adapter as LogAdapter).setList(it)
        }
    }
}