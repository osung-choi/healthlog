package com.example.healthlog.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.healthlog.R
import com.example.healthlog.databinding.FragmentMainBinding
import com.example.healthlog.viewmodel.MainViewModel
import com.example.healthlog.model.calendar.OneDayDecorator
import com.example.healthlog.model.calendar.SaturdayDecorator
import com.example.healthlog.model.calendar.SundayDecorator
import com.example.healthlog.utils.Define
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode


class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = mainViewModel

        initUi()
        initObserve()

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

    private fun initObserve() {
        mainViewModel.startExcecise.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, getString(R.string.msg_cheer), Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, TimerActivity::class.java))

            activity!!.overridePendingTransition(R.anim.slide_right_in, R.anim.no_move) //showAnim , hideAnum
        })

        mainViewModel.editExerciseLog.observe(viewLifecycleOwner, Observer {
            binding.mainCalendar.clearSelection() //달력 클릭 이미지 제거

            startActivity(Intent(context, LogActivity::class.java)
                .putExtra(Define.INTENT_WRITE_LOG_DATE, it))

            activity!!.overridePendingTransition(R.anim.slide_right_in, R.anim.no_move)
        })
    }
}
