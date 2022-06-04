package com.ahimsarijalu.investin.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahimsarijalu.investin.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}