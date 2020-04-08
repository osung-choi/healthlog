package com.example.healthlog.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ExerciseItem",
    foreignKeys = [
        ForeignKey (
            entity = Part::class,
            parentColumns = ["name"],
            childColumns = ["part"]
        )
    ]
)

data class Exercise(
    @PrimaryKey(autoGenerate = true) val seq: Int, //Exercise Seq
    val name: String, //운동 명
    val part: String //운동 부위
)