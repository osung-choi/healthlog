package com.example.healthlog.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.healthlog.R
import com.example.healthlog.databinding.ActivityAddPartBinding
import com.example.healthlog.viewmodel.AddPartViewModel

class AddPartActivity : AppCompatActivity() {

    private lateinit var viewModel: AddPartViewModel
    private lateinit var binding: ActivityAddPartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddPartViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_part)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this //LiveData

        initUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        
        overridePendingTransition(R.anim.no_move, R.anim.slide_right_out)
    }

    private fun initUi() {
        setSupportActionBar(binding.addPartToolbar.toolBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.addPartToolbar.toolbarTitle.text = getString(R.string.title_add_part)
    }
}
