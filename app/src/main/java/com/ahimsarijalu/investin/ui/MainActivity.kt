package com.ahimsarijalu.investin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivityMainBinding
import com.ahimsarijalu.investin.ui.onboarding.OnBoardingActivity
import com.ahimsarijalu.investin.ui.roles.RolesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        setupView()

    }

    private fun setupView() {
        val sharedPref = this.getSharedPreferences("isFirstTime", Context.MODE_PRIVATE)
        val currentUser = auth.currentUser

        if (sharedPref.getBoolean("isFirstTime", true)) {
            Intent(this, OnBoardingActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else if (currentUser == null) {
            Intent(this, RolesActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else {
            val navView: BottomNavigationView = binding.navView
            val navController =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!
                    .findNavController()
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }
}