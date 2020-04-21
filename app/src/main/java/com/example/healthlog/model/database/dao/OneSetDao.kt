package com.example.healthlog.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.model.database.entitiy.OneSet
import io.reactivex.Maybe

@Dao
interface OneSetDao : BaseDao<OneSet> {
    @Query("select * from OneSet where OneSet.exerciseSeq = :exerciseSeq")
    fun selectOneSetForExercise(exerciseSeq: Int) : List<OneSet>

    @Query("SELECT * FROM OneSet")
    fun selectAllOnSet() : Maybe<List<OneSet>>
}