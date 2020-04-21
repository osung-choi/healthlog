package com.example.healthlog.model.database.entitiy

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["name", "part"],
    foreignKeys = [
        ForeignKey (
            entity = Part::class,
            parentColumns = ["name"],
            childColumns = ["part"]
        )
    ]
)

data class ExerciseItem(
    val name: String, //운동 명
    val part: String //운동 부위
)

