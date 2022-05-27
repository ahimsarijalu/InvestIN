package com.ahimsarijalu.investin.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.ui.home.HomeActivity
import com.ahimsarijalu.investin.ui.onboarding.OnBoardingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getSharedPreferences("isFirstTime", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("isFirstTime", true)) {
            Intent(this, OnBoardingActivity::class.java).apply {
                startActivity(this)
                finish()
            }

        } else {
            Intent(this, HomeActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}