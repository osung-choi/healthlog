package com.example.healthlog.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.R
import com.example.healthlog.data.ExerciseLog

class LogAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val logDataList = arrayListOf<ExerciseLog>()

    fun setItem(item: ExerciseLog) {
        logDataList.add(item)
        notifyItemInserted(logDataList.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LogViewHolder(parent)

    override fun getItemCount() = logDataList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    inner class LogViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_exercise_log, parent, false)
    ) {
    }

}