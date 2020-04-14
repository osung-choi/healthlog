package com.example.healthlog.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.database.entitiy.ExerciseLog
import com.example.healthlog.databinding.AdapterExerciseLogBinding
import com.example.healthlog.diff.ExerciseLogDiffCallback
import com.example.healthlog.utils.Define

class LogAdapter: RecyclerView.Adapter<LogAdapter.LogViewHolder>() {
    private var logDataList = arrayListOf<ExerciseLog>()

    fun setList(list: ArrayList<ExerciseLog>) {
        val diffResult = DiffUtil.calculateDiff(ExerciseLogDiffCallback(logDataList, list))
        logDataList = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = LogViewHolder(AdapterExerciseLogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = logDataList.size
    override fun onBindViewHolder(holder: LogViewHolder, position: Int) = holder.bind(logDataList[position])

    inner class LogViewHolder(binding: AdapterExerciseLogBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding = binding
        private val setAdapter by lazy { SetAdapter(logDataList[adapterPosition].setList) }

        fun bind(item : ExerciseLog) {
            binding.logData = item
            binding.setList.run {
                layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
                adapter = setAdapter
            }

            binding.subSet.setOnClickListener {
                if(item.setCount > 1) {
                    item.setCount = item.setCount - 1
                    binding.countSet.text = item.setCount.toString()

                    setAdapter.removeLastItem()
                }
            }

            binding.addSet.setOnClickListener {
                if(item.setCount < 30) {
                    item.setCount = item.setCount + 1
                    binding.countSet.text = item.setCount.toString()

                    setAdapter.addItem()
                }
            }

            binding.setDelete.setOnClickListener {
                logDataList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

}