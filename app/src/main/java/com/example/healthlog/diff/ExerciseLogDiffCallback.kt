package com.example.healthlog.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.healthlog.model.database.entitiy.ExerciseLog

class ExerciseLogDiffCallback(oldList: List<ExerciseLog>, newList: List<ExerciseLog>): DiffUtil.Callback() {
    val oldList = oldList
    val newList = newList


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldList[oldItemPosition].seq == newList[newItemPosition].seq

    override fun getOldListSize() = oldList.size


    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldList[oldItemPosition] == newList[newItemPosition]

}