package com.example.healthlog.timer

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.healthlog.R
import com.example.healthlog.databinding.ActivityTimerBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class TimerActivity : AppCompatActivity() {
    private val TAG = TimerActivity::class.java.simpleName

    private lateinit var binding: ActivityTimerBinding
    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_timer)
        binding.viewModel = timerViewModel

        initUi()
        initObserve()

        timerViewModel.startTimer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUi() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //toolbar 뒤로가기 버튼

        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false) //기본 toolbar title 비활성화

        binding.toolbarTitle.text = getString(R.string.timer_title)

        binding.npMinute.minValue = 0
        binding.npMinute.maxValue = 60
        binding.npSecond.minValue = 0
        binding.npSecond.maxValue = 60
    }

    private fun initObserve() {

    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.no_move, R.anim.slide_right_out)
    }
}
