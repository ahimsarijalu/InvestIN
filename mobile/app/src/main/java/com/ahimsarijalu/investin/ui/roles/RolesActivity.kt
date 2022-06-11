package com.ahimsarijalu.investin.ui.roles

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahimsarijalu.investin.databinding.ActivityRolesBinding
import com.ahimsarijalu.investin.ui.auth.SignInActivity

class RolesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRolesBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRolesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        sharedPref = this.getSharedPreferences("isInvestor", Context.MODE_PRIVATE)

        setupAction()
    }

    private fun setupAction() {
        binding.boBtn.setOnClickListener {
            Intent(this@RolesActivity, SignInActivity::class.java).apply {
                with (sharedPref.edit()) {
                    putBoolean("isInvestor", false)
                    apply()
                }
                startActivity(this)
            }
        }

        binding.investorBtn.setOnClickListener {
            Intent(this@RolesActivity, SignInActivity::class.java).apply {
                with (sharedPref.edit()) {
                    putBoolean("isInvestor", true)
                    apply()
                }
                startActivity(this)
            }
        }

    }
}