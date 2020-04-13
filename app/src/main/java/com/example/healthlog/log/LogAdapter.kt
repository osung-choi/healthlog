package com.example.healthlog.log

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.database.entitiy.ExerciseItem
import com.example.healthlog.database.entitiy.ExerciseLog
import com.example.healthlog.databinding.AdapterExerciseLogBinding
import com.example.healthlog.diff.ExerciseLogDiffCallback

class LogAdapter: RecyclerView.Adapter<LogAdapter.LogViewHolder>() {
    private var logDataList = arrayListOf<ExerciseLog>()

    fun setList(list: ArrayList<ExerciseLog>) {
        val diffResult = DiffUtil.calculateDiff(ExerciseLogDiffCallback(logDataList, list))
        logDataList = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = LogViewHolder(AdapterExerciseLogBinding.inflate(LayoutInflater.from(parent!!.context), parent, false))
    override fun getItemCount() = logDataList.size
    override fun onBindViewHolder(holder: LogViewHolder, position: Int) = holder.bind(logDataList[position])

    inner class LogViewHolder(binding: AdapterExerciseLogBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding = binding

        fun bind(item : ExerciseLog) {
            binding.logData = item

            binding.subSet.setOnClickListener {
                item.setCount = item.setCount - 1
                binding.countSet.text = item.setCount.toString()
            }

            binding.addSet.setOnClickListener {
                item.setCount = item.setCount + 1
                binding.countSet.text = item.setCount.toString()
            }

            binding.setDelete.setOnClickListener {
                logDataList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

}