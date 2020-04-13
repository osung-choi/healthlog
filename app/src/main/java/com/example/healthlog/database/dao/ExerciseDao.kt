package com.example.healthlog.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.database.entitiy.ExerciseItem
import io.reactivex.Maybe


@Dao
interface ExerciseDao: BaseDao<ExerciseItem> {
    @Query("SELECT * FROM ExerciseItem")
    fun selectExerciseAllItem() : Maybe<List<ExerciseItem>>

    @Query("select count(*) from ExerciseItem as item where item.name = :name and item.part = :part")
    fun selectExerciseItem(name: String, part: String) : Int
}