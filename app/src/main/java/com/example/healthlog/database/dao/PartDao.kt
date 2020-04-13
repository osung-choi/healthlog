package com.example.healthlog.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.database.entitiy.Part
import io.reactivex.Maybe

@Dao
interface PartDao: BaseDao<Part> {
    @Query("SELECT * FROM Part")
    fun selectPart() : Maybe<List<Part>>
}