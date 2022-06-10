package com.ahimsarijalu.investin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivityMainBinding
import com.ahimsarijalu.investin.ui.onboarding.OnBoardingActivity
import com.ahimsarijalu.investin.ui.roles.RolesActivity
import com.ahimsarijalu.investin.utils.getToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

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
            getToken()
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            navController = navHostFragment.navController

            val popupMenu = PopupMenu(this, null)
            popupMenu.inflate(R.menu.bottom_nav_menu)
            val menu = popupMenu.menu
            setupActionBarWithNavController(navController)
            binding.navView.setupWithNavController(menu, navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}