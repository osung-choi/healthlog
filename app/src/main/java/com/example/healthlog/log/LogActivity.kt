package com.example.healthlog.log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.healthlog.R
import com.example.healthlog.databinding.ActivityLogBinding

class LogActivity : AppCompatActivity() {

    private lateinit var logViewModel: LogViewModel
    private lateinit var binding: ActivityLogBinding
    private lateinit var searchAdapter: SearchAdapter

    private val searchList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logViewModel = ViewModelProviders.of(this).get(LogViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log)
        binding.viewModel = logViewModel
        binding.lifecycleOwner = this

        initUi()
        initObserve()
    }

    private fun initUi() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.toolbarTitle.text = getString(R.string.log_title)

        searchAdapter = SearchAdapter(this, android.R.layout.simple_dropdown_item_1line, searchList)
        binding.autoSearchView.setAdapter(searchAdapter)
    }

    private fun initObserve() {
        logViewModel.searchTextChange.observe(this, Observer {
            setAutoSearchView(it)
        })
    }

    private fun setAutoSearchView(searchList: List<String>) {
        searchAdapter.setData(searchList)
        searchAdapter.notifyDataSetChanged()
    }
}
