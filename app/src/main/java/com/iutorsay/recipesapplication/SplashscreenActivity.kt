package com.iutorsay.recipesapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.concurrent.TimeUnit
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v4.os.HandlerCompat.postDelayed
import android.os.CountDownTimer







class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        object : CountDownTimer(1000, 1000) {
            override fun onFinish() {
                val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)

                startActivity(intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start()
    }
}
