package com.example.healthlog.database.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T) : List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}