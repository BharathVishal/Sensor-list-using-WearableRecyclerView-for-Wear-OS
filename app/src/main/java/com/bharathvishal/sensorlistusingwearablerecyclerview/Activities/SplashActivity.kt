package com.bharathvishal.sensorlistusingwearablerecyclerview.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private var actvityContext: Context? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actvityContext = this@SplashActivity

        //For testing purpose
        //1 - Default Main Activity with xml
        //2 - Main activity with Jetpack Compose
        val activityTypeToLaunch = 1

        if (activityTypeToLaunch == 1) {
            val intent = Intent(actvityContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(actvityContext, MainActivityCompose::class.java)
            startActivity(intent)
            finish()
        }
    }


    private val isOSSandAbove: Boolean
        get() {
            val sdkInt = Build.VERSION.SDK_INT
            return sdkInt >= 31
        }
}