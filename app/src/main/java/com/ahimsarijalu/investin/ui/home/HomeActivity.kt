package com.ahimsarijalu.investin.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahimsarijalu.investin.databinding.ActivityHomeBinding
import com.ahimsarijalu.investin.databinding.ActivityOnBoardingBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}