package com.example.healthlog.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Part(
    @PrimaryKey() val name: String //운동 부위 명
)