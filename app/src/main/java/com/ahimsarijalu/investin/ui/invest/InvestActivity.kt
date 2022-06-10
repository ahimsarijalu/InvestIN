package com.ahimsarijalu.investin.ui.invest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.investin.data.datasource.remote.response.UserDataItem
import com.ahimsarijalu.investin.data.repository.Result
import com.ahimsarijalu.investin.databinding.ActivityInvestBinding
import com.ahimsarijalu.investin.utils.ViewModelFactory
import com.ahimsarijalu.investin.utils.showToast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class InvestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvestBinding

    private lateinit var investViewModel: InvestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInvestBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()

        val userId = intent.getStringExtra(EXTRA_UID)

        if (userId != null) {
            showUser(userId)
        }
    }

    private fun showUser(userId: String) {
        binding.root.visibility = View.GONE
        investViewModel.getUserById(userId)
            .observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            showToast(this, result.error)
                        }
                        is Result.Success -> {
                            binding.root.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            setUserData(result.data.data[0])
                        }
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

    private fun setUserData(user: UserDataItem) {
        binding.let {
            Glide.with(this)
                .load(user.avatar)
                .circleCrop()
                .into(it.userAvatar)
        }
        binding.apply {
            val location = "${user.city}, ${user.province}"

            nameTv.text = user.displayName
            categoryTv.text = user.category
            locationTv.text = location
            descTv.text = user.bio
        }

        setupInvestButton()
        setupButtonAction(user)
    }

    private fun setupButtonAction(user: UserDataItem) {
        binding.apply {
            emailButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.setDataAndType(Uri.parse("mailto:${user.email}"), "plain/text")
                startActivity(Intent.createChooser(intent, "Select your Email app"))
            }

            investBtn.setOnClickListener {
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
                    putExtra(Intent.EXTRA_SUBJECT, "Invest Proposal")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "I am interested in your business, I want to invest in your business"
                    )
                    startActivity(this)
                }
            }
        }
    }

    private fun setupViewModel() {
        investViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[InvestViewModel::class.java]
    }

    private fun setupInvestButton() {
        val currentUser = Firebase.auth.currentUser
        val db = Firebase.firestore

        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { result ->

                    val userData = result.toObject<UserDataItem>()

                    if (userData != null) {
                        if (userData.investorRole) {
                            binding.investBtn.visibility = View.GONE
                        }
                    }


                }
        }
    }

    companion object {
        const val EXTRA_UID = "extra_uid"
    }
}