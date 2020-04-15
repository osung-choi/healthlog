package com.example.healthlog.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = ExerciseLog::class,
            parentColumns = ["seq"],
            childColumns = ["exerciseSeq"]
        )
    ]
)
data class OneSet(
    @PrimaryKey(autoGenerate = true) val seq: Int,
    var exerciseSeq: Int, //어떤 운동 로그의 세트인지
    val setNum: Int, //몇번째 세트인지
    val weight: Int, //횟수
    val count: Int //무게
)