package com.example.healthlog.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.database.entitiy.ExerciseLog

@Dao
interface ExerciseLogDao: BaseDao<ExerciseLog> {

}
