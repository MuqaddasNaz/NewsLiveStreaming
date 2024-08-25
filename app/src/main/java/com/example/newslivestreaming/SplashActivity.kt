package com.example.newslivestreaming

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {


    private var context: Context = this@SplashActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Functions.setTransparentStatusBar(context)

        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this, LiveStreamActivity::class.java))

            finish()
        }, 2000)
    }
}