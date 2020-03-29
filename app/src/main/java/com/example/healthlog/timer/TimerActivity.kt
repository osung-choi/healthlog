package com.example.healthlog.timer

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.NumberPicker
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

        //얘내 위치는 음...
        binding.npMinute.minValue = 0
        binding.npMinute.maxValue = 14
        binding.npSecond.minValue = 0
        binding.npSecond.maxValue = 59
    }

    private fun initObserve() {
        timerViewModel.mMinimalWindowStatus.observe(this, Observer {
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(Intent(this, TimerService::class.java))
            }
            else {
                startService(Intent(this, TimerService::class.java))
            }

            startActivity(
                Intent(Intent.ACTION_MAIN) //태스크의 첫 액티비티로 시작
                    .addCategory(Intent.CATEGORY_HOME) //홈화면 표시
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //새로운 태스크를 생성하여 그 태스크안에서 액티비티 추가
            )
        })

    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.no_move, R.anim.slide_right_out)
    }
}
