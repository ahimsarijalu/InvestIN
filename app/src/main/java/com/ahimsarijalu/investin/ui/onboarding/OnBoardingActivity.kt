package com.ahimsarijalu.investin.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivityOnBoardingBinding
import com.ahimsarijalu.investin.ui.roles.RolesActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupAction()
    }

    private fun setupView() {
        val pagerAdapter = OnBoardingPagerAdapter(this)
        viewPager = binding.onBoardingPager
        viewPager.adapter = pagerAdapter
        viewPager.isUserInputEnabled = false
    }

    private fun setupAction() {
        binding.nextButton.setOnClickListener {
            if (viewPager.currentItem == 2) {
                val sharedPref = this.getSharedPreferences("isFirstTime", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isFirstTime", false)
                    apply()
                }
                Intent(this, RolesActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            viewPager.currentItem += 1
            updateNextButton(viewPager.currentItem)
        }
    }

    private fun updateNextButton(position: Int) {
        when (position) {
            0 -> binding.nextButton.setImageResource(R.drawable.ic_onboarding_1_btn)
            1 -> binding.nextButton.setImageResource(R.drawable.ic_onboarding_2_btn)
            2 -> binding.nextButton.setImageResource(R.drawable.ic_onboarding_3_btn)
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem -= 1
            updateNextButton(viewPager.currentItem)
        }
    }

    inner class OnBoardingPagerAdapter(activity: FragmentActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = FirstOnBoardingFragment()
                1 -> fragment = SecondOnBoardingFragment()
                2 -> fragment = ThirdOnBoardingFragment()
            }
            return fragment as Fragment
        }
    }
}