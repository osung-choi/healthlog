package com.example.healthlog.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.healthlog.R
import com.example.healthlog.databinding.FragmentMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = mainViewModel

        initUi()

        return binding.root
    }

    fun initUi() {
        binding.mainCalendar.state().edit()
            .setMinimumDate(CalendarDay.from(2017, 1, 1))
            .setMaximumDate(CalendarDay.from(2030, 12, 31))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        binding.mainCalendar.addDecorators(
            SaturdayDecorator(),
            SundayDecorator(),
            OneDayDecorator()
        )
    }
}
