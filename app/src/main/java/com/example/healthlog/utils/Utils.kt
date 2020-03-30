package com.example.healthlog.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat.getSystemService


object Utils {
    fun setFullScreen(activity: Activity) {
        //상태 표시줄 숨기기
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val uiOption = activity.window.decorView.systemUiVisibility
        var newUiOption = uiOption

        newUiOption = newUiOption xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOption = newUiOption xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOption = newUiOption xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        activity.window.decorView.systemUiVisibility = newUiOption
    }

    fun isServiceRunning(context: Context, serviceClass: Class<*>) : Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.className)) {
                return true
            }
        }
        return false
    }

}