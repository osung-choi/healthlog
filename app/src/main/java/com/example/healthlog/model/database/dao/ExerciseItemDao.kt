package com.example.healthlog.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.model.database.entitiy.ExerciseItem
import io.reactivex.Maybe


@Dao
interface ExerciseItemDao: BaseDao<ExerciseItem> {
    @Query("SELECT * FROM ExerciseItem")
    fun selectExerciseAllItem() : Maybe<List<ExerciseItem>>

    @Query("select count(*) from ExerciseItem as item where item.name = :name and item.part = :part")
    fun selectExerciseItem(name: String, part: String) : Int
}