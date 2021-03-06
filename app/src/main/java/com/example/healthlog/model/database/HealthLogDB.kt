package com.example.healthlog.model.database

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.healthlog.model.database.dao.ExerciseItemDao
import com.example.healthlog.model.database.dao.ExerciseLogDao
import com.example.healthlog.model.database.dao.OneSetDao
import com.example.healthlog.model.database.dao.PartDao
import com.example.healthlog.model.database.entitiy.ExerciseItem
import com.example.healthlog.model.database.entitiy.ExerciseLog
import com.example.healthlog.model.database.entitiy.OneSet
import com.example.healthlog.model.database.entitiy.Part
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

@Database(
    entities = [ExerciseItem::class, ExerciseLog::class, OneSet::class, Part::class],
    version = 1)
abstract class HealthLogDB: RoomDatabase() {
    abstract fun getPartDao() : PartDao
    abstract fun getExerciseDao() : ExerciseItemDao
    abstract fun getExerciseLogDao() : ExerciseLogDao
    abstract fun getOneSetDao() : OneSetDao

    companion object {
        @Volatile private var INSTANCE: HealthLogDB? = null

        fun getInstance(): HealthLogDB? = synchronized(this) {
            return INSTANCE
        }

        fun createDatebase(context: Context) {
            INSTANCE = buildDatabase(context)
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                HealthLogDB::class.java, "HealthLogDB2.db")
                //.addMigrations(MIGRATION_1_2)
                .addCallback(object : RoomDatabase.Callback() {
                    @SuppressLint("CheckResult")
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val partList = arrayListOf(Part("가슴"),
                            Part("등"),
                            Part("이두"),
                            Part("삼두"),
                            Part("어깨"),
                            Part("대퇴사두"),
                            Part("대퇴이두"))

                        val exerciseList = arrayListOf(ExerciseItem("벤치프레스", "가슴"),
                            ExerciseItem("덤벨프레스", "가슴"),
                            ExerciseItem("랫풀다운", "등")
                        )

                        Observable.fromIterable(partList)
                            .subscribeOn(Schedulers.io())
                            .subscribe {
                                Log.d("asd", "save : $it")
                                getInstance()?.getPartDao()?.insert(it)
                            }

                        Observable.fromIterable(exerciseList)
                            .subscribeOn(Schedulers.io())
                            .subscribe ({
                                Log.d("asd", "save : $it")
                                getInstance()?.getExerciseDao()?.insert(it)
                            }, {
                                it.printStackTrace()
                            })
                    }
                })
                .build()

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                //ALTER TABLE 테이블명 ADD COLUMN 컬럼명 [데이터 타입];
                //
            }
        }
    }
}