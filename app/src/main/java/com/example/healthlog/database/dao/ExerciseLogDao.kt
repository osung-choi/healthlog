package com.example.healthlog.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.database.entitiy.ExerciseLog
import com.example.healthlog.database.entitiy.OneSet
import io.reactivex.Maybe

@Dao
interface ExerciseLogDao: BaseDao<ExerciseLog> {
    @Query("SELECT * FROM ExerciseLog")
    fun selectAllExerciseLog() : Maybe<List<ExerciseLog>>
}
