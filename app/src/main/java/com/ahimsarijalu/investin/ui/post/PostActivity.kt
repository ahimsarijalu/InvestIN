package com.ahimsarijalu.investin.ui.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.data.repository.Result
import com.ahimsarijalu.investin.databinding.ActivityPostBinding
import com.ahimsarijalu.investin.ui.MainActivity
import com.ahimsarijalu.investin.ui.home.HomeFragment
import com.ahimsarijalu.investin.utils.*
import com.bumptech.glide.Glide
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

@ExperimentalSerializationApi
class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private lateinit var postViewModel: PostViewModel

    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.create_post)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
        setupAction()

    }

    private fun setupViewModel() {
        postViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[PostViewModel::class.java]
    }

    private fun setupAction() {
        binding.postBtn.setOnClickListener {
            val text = binding.postInput.editText?.text.toString()

            if (file != null) {
                postViewModel.addExplore(text).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Error -> {
                                showToast(this, result.error)
                            }
                            is Result.Success -> {
                                postViewModel.uploadImage(
                                    token.value.toString(),
                                    file!!,
                                    result.data.data.id, object : ApiCallback {
                                        override fun onResponse(success: Boolean) {
                                            if (success) {
                                                showToast(
                                                    applicationContext,
                                                    "Post added successfully"
                                                )
                                                Intent(
                                                    applicationContext,
                                                    MainActivity::class.java
                                                ).apply {
                                                    startActivity(this)
                                                    finishAffinity()
                                                }
                                            }
                                        }

                                    }
                                )

                            }
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                        }
                    }

                }

            } else {
                showToast(this, "Choose an Image first")
            }
        }

        binding.previewImage.setOnClickListener {
            imageChooser(launcherIntentGalery)
        }
    }

    private val launcherIntentGalery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            file = myFile
            Glide.with(this).load(selectedImg).centerCrop().into(binding.previewImage)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}