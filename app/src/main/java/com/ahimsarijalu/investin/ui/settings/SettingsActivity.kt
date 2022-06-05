package com.ahimsarijalu.investin.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.settings)

    }
}