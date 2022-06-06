package com.ahimsarijalu.investin.ui.settings.edit_financial_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivityEditFinancialDataBinding

class EditFinancialDataActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditFinancialDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditFinancialDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.edit_financial_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}