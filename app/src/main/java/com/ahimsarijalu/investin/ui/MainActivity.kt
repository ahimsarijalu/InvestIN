package com.ahimsarijalu.investin.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.ui.home.HomeActivity
import com.ahimsarijalu.investin.ui.onboarding.OnBoardingActivity
import com.ahimsarijalu.investin.ui.roles.RolesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        val sharedPref = this.getSharedPreferences("isFirstTime", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("isFirstTime", true)) {
            Intent(this, OnBoardingActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else if (currentUser != null) {
            Intent(this, HomeActivity::class.java).apply {
                putExtra("user", auth.currentUser)
                startActivity(this)
                finish()
            }
        } else {
            Intent(this, RolesActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

}