package com.example.healthlog.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.model.database.entitiy.ExerciseLog
import io.reactivex.Maybe

@Dao
interface ExerciseLogDao: BaseDao<ExerciseLog> {
    @Query("select * from ExerciseLog where ExerciseLog.date = :date")
    fun selectExerciseLogForDate(date: String): Maybe<List<ExerciseLog>>

    @Query("SELECT * FROM ExerciseLog")
    fun selectAllExerciseLog() : Maybe<List<ExerciseLog>>

    @Query("delete from ExerciseLog where ExerciseLog.date = :date ")
    fun deleteExerciseLog(date: String)
}
