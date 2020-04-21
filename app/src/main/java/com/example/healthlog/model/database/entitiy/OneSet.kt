package com.example.healthlog.model.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = ExerciseLog::class,
            parentColumns = ["seq"],
            childColumns = ["exerciseSeq"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OneSet(
    @PrimaryKey(autoGenerate = true) val seq: Int,
    var exerciseSeq: Int, //어떤 운동 로그의 세트인지
    val setNum: Int, //몇번째 세트인지
    var weight: Int, //횟수
    var count: Int //무게
)