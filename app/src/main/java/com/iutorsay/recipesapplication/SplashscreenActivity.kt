package com.iutorsay.recipesapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity


class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        object : CountDownTimer(1000, 1000) {
            override fun onFinish() {
                val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)

                startActivity(intent)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start()
    }
}
