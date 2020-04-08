package com.example.healthlog.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OneSet(
    @PrimaryKey(autoGenerate = true) val seq: Int,
    val setNum: Int, //몇번째 세트인지
    val weight: Int, //횟수
    val count: Int //무게
)