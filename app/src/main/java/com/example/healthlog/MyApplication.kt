package com.example.healthlog

import android.app.Application
import com.example.healthlog.database.HealthLogDB

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        HealthLogDB.createDatebase(this)

    }
}