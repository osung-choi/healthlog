package com.example.healthlog.database.entitiy

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [
//        ForeignKey(entity = ExerciseItem::class,
//            parentColumns = ["name","part"],
//            childColumns = ["exerciseSeq"]
//        )
//    ]
)
data class ExerciseLog (
    @PrimaryKey(autoGenerate = true) val seq: Int, //Log Seq
    val date: String, //운동 날짜 yyyy-MM-dd
    @Embedded val item: ExerciseItem, //운동 종류 - foreign key(Exercise)
    val unit: String,
    var setCount: Int //진행한 세트 수
) {
//    private val setList = arrayListOf<OneSet>()
//
//    fun addSetItem(itemCount: Int) {
//        for(i in 0 until itemCount) {
//            setList.add(OneSet(0, seq, setList.size + 1, 0, 0))
//        }
//    }
//
//    fun removeSetItem(index: Int) = setList.removeAt(index)
//    fun getSetList() = setList

    override fun equals(other: Any?): Boolean {
        if (other is ExerciseLog ){
            return seq == other.seq
        }

        return false
    }
}