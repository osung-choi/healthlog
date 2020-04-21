package com.example.healthlog.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.healthlog.model.database.entitiy.Part
import io.reactivex.Maybe

@Dao
interface PartDao: BaseDao<Part> {
    @Query("SELECT * FROM Part")
    fun selectPart() : Maybe<List<Part>>
}