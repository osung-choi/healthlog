package com.example.healthlog.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthlog.R
import com.example.healthlog.databinding.ActivityLogBinding
import com.example.healthlog.adapter.LogAdapter
import com.example.healthlog.viewmodel.LogViewModel
import com.example.healthlog.utils.Define
import com.example.healthlog.utils.Utils


class LogActivity : AppCompatActivity() {

    private lateinit var logViewModel: LogViewModel
    private lateinit var binding: ActivityLogBinding
    private lateinit var logAdapter: LogAdapter
    private lateinit var logDate: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logViewModel = ViewModelProviders.of(this).get(LogViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log)
        binding.viewModel = logViewModel
        binding.lifecycleOwner = this

        logDate = intent.getStringExtra(Define.INTENT_WRITE_LOG_DATE)

        initUi()
        initObserve()
    }

    override fun onResume() {
        super.onResume()

        binding.viewModel?.loadExerciseLogData(logDate)
    }

    override fun onPause() {
        super.onPause()

        binding.viewModel?.saveExerciseLogData(logDate)
    }

    private fun initUi() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.toolbarTitle.text = getString(R.string.log_title)


        with(binding.logList) {
            layoutManager = LinearLayoutManager(this@LogActivity)
            logAdapter = LogAdapter()

            adapter = logAdapter
        }


        logViewModel.getExerciseList(logDate)
    }

    private fun initObserve() {
        with(logViewModel) {
            addExerciseLog.observe(this@LogActivity, Observer {
                clearSearchText()
            })

            exerciseList.observe(this@LogActivity, Observer {
                binding.autoSearchView.setAdapter(ArrayAdapter(
                    this@LogActivity, android.R.layout.simple_dropdown_item_1line, it)
                )
            })
        }
    }

    private fun clearSearchText() {
        binding.autoSearchView.setText("")
        binding.autoSearchView.clearFocus()

        Utils.keyboardHide(this, binding.autoSearchView)
    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.no_move, R.anim.slide_right_out)
    }
}
