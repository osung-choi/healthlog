package com.example.healthlog.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(entity = Exercise::class,
            parentColumns = ["seq"],
            childColumns = ["seq"]
        )
    ]
)
data class ExerciseLog (
    @PrimaryKey(autoGenerate = true) val seq: Int, //Log Seq
    val date: String, //운동 날짜 yyyy-MM-dd
    val exerciseSeq: Int, //운동 종류 - foreign key(Exercise)
    val setCount: Int //진행한 세트 수
   // val oneSet: List<OneSet> //세트 내용
)