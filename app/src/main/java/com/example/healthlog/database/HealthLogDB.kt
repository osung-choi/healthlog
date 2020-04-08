package com.example.healthlog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.healthlog.database.dao.PartDAO
import com.example.healthlog.database.entitiy.Exercise
import com.example.healthlog.database.entitiy.ExerciseLog
import com.example.healthlog.database.entitiy.Part
import com.example.healthlog.database.entitiy.OneSet

@Database(
    entities = [Exercise::class, ExerciseLog::class, OneSet::class, Part::class],
    version = 1)
abstract class HealthLogDB: RoomDatabase() {
    abstract fun getPartDAO() : PartDAO

    companion object {
        @Volatile private var INSTANCE: HealthLogDB? = null

        fun getInstance(context: Context): HealthLogDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                HealthLogDB::class.java, "HealthLogDB.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Thread {
                            getInstance(context).getPartDAO().insert(
                                Part("가슴"),
                                Part("등"),
                                Part("이두"),
                                Part("삼두"),
                                Part("어깨"),
                                Part("대퇴사두"),
                                Part("대퇴이두")
                            )
                        }.start()

                    }
                })
                .build()
    }
}