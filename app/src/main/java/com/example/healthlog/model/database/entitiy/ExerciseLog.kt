package com.example.healthlog.model.database.entitiy

import androidx.room.*
import com.example.healthlog.utils.Define

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
    @Ignore
    var setList = ArrayList<OneSet>()

    init {
        setDefaultSetList(Define.DEFAULT_SET_COUNT)
    }

    private fun setDefaultSetList(count: Int) {
        for(i in 1 .. count) {
            setList.add(OneSet(0, 0, setList.size + 1, 0, 0))
        }
    }

//    private fun defaultSetItem(exerciseSeq: Int) = OneSet(0, exerciseSeq, setList.size + 1, 0, 0)
//
//    fun addItem(exerciseSeq: Int) = if(setList.size > 0)
//        setList.add(setList.get(setList.lastIndex))
//    else
//        setList.add(defaultSetItem(exerciseSeq))
//
//    fun removeLastItem() {
//        val index = setList.size-1
//        setList.removeAt(index)
//    }

    override fun equals(other: Any?): Boolean {
        if (other is ExerciseLog ){
            return seq == other.seq
        }

        return false
    }
}