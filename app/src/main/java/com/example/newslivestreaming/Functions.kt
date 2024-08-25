package com.example.newslivestreaming

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.Window


object Functions {

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


    fun setTransparentStatusBar(context: Context) {

        val activity = context as Activity

        val window: Window = activity.window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

    }

}