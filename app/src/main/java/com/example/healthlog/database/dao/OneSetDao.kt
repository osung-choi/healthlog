package com.example.healthlog.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.database.entitiy.OneSet
import io.reactivex.Maybe

@Dao
interface OneSetDao : BaseDao<OneSet> {
    @Query("SELECT * FROM OneSet")
    fun selectAllOnSet() : Maybe<List<OneSet>>
}