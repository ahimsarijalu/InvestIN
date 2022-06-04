package com.ahimsarijalu.investin.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahimsarijalu.investin.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}