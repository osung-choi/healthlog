package com.example.healthlog.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.healthlog.MainActivity
import com.example.healthlog.R
import com.example.healthlog.databinding.ActivityTimerBinding
import com.example.healthlog.service.TimerService
import com.example.healthlog.viewmodel.TimerViewModel
import com.example.healthlog.utils.PrefMananger


class TimerActivity : AppCompatActivity() {
    private val TAG = TimerActivity::class.java.simpleName

    private lateinit var binding: ActivityTimerBinding
    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_timer)
        binding.viewModel = timerViewModel
        binding.lifecycleOwner = this

        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(Intent(this, TimerService::class.java))
        }
        else {
            startService(Intent(this, TimerService::class.java))
        }

        initUi()
        initObserve()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
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

        val prefManager = PrefMananger()
        val minute = prefManager.getInt(this, PrefMananger.Key.PREF_STOPWATCH_MINUTE)
        val second = prefManager.getInt(this, PrefMananger.Key.PREF_STOPWATCH_SECOND)

        timerViewModel.initViewModel(minute, second)

        //얘내 위치는 음...
        binding.npMinute.minValue = 0
        binding.npMinute.maxValue = 14
        binding.npSecond.minValue = 0
        binding.npSecond.maxValue = 59
    }

    private fun initObserve() {
        timerViewModel.mStartStopWatch.observe(this, Observer {
            val prefManager = PrefMananger()
            prefManager.setInt(this, PrefMananger.Key.PREF_STOPWATCH_MINUTE, it.first)
            prefManager.setInt(this, PrefMananger.Key.PREF_STOPWATCH_SECOND, it.second)
        })

        timerViewModel.mMinimalWindowStatus.observe(this, Observer {
            if(it) {
                startActivity(
                    Intent(Intent.ACTION_MAIN) //태스크의 첫 액티비티로 시작
                        .addCategory(Intent.CATEGORY_HOME) //홈화면 표시
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //새로운 태스크를 생성하여 그 태스크안에서 액티비티 추가
                )
            }
        })

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.dialog_continue_exersice)
            .setPositiveButton(R.string.dialog_btn_continue) { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }.setNegativeButton(R.string.dialog_btn_finish) { dialog, _ ->
                stopService(Intent(this, TimerService::class.java))

                dialog.dismiss()
                super.onBackPressed()
            }
            .show()
    }

    override fun finish() {
        super.finish()
        startActivity(Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        overridePendingTransition(R.anim.no_move, R.anim.slide_right_out)
    }
}
