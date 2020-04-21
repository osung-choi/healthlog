package com.example.healthlog.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.healthlog.R
import com.example.healthlog.viewmodel.AddPartViewModel

class AddPartFragment : Fragment() {

    companion object {
        fun newInstance() = AddPartFragment()
    }

    private lateinit var viewModel: AddPartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_part, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddPartViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
