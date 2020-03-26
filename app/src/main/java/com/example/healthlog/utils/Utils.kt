package com.example.healthlog.utils

import android.app.Activity
import android.view.View

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
}