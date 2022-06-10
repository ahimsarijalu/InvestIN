package com.ahimsarijalu.investin.ui.settings.edit_profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.data.datasource.remote.response.UserDataItem
import com.ahimsarijalu.investin.databinding.ActivityEditProfileBinding
import com.ahimsarijalu.investin.ui.MainActivity
import com.ahimsarijalu.investin.utils.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

@ExperimentalSerializationApi
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var editProfileViewModel: EditProfileViewModel

    private var file: File? = null
    private val launcherIntentGalery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            file = myFile
            Glide.with(this).load(selectedImg).centerCrop().into(binding.avatarPreview)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        if (user != null) {
            setupForm(user, db)
            setupAction(user, db)
        }

        editProfileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[EditProfileViewModel::class.java]
    }

    private fun setupAction(user: FirebaseUser, db: FirebaseFirestore) {
        binding.saveProfileBtn.setOnClickListener {
            updateToFirestore(user, db)
        }

        binding.avatarPreview.setOnClickListener {
            imageChooser(launcherIntentGalery)
        }
    }

    private fun setupForm(user: FirebaseUser, db: FirebaseFirestore) {
        db.collection("users")
            .document(user.uid)
            .get()
            .addOnSuccessListener { result ->
                val userData = result.toObject<UserDataItem>()!!

                binding.apply {
                    nameTextField.editText?.setText(userData.displayName)
                    categoryTextField.editText?.setText(userData.category)
                    bioTextField.editText?.setText(userData.bio)
                    cityTextField.editText?.setText(userData.city)
                    provinceTextField.editText?.setText(userData.province)
                    Glide.with(applicationContext).load(userData.avatar).circleCrop()
                        .into(avatarPreview)
                    setupCategory()
                }
            }
    }

    private fun setupCategory() {
        val items = listOf(
            "Entertainment",
            "Science and Technology",
            "Law and Order",
            "Sport",
            "Fashion",
            "Healthcare",
            "Food and Beverage",
            "Automotive",
            "Toys and Hobbies",
            "Education",
            "Finance",
            "Marketplace",
            "Assurance",
            "Transportation and Accommodation"
        )
        val adapter = ArrayAdapter(this, R.layout.category_list_item, items)
        (binding.categoryTextField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun updateToFirestore(user: FirebaseUser, db: FirebaseFirestore) {

        val name = binding.nameTextField.editText?.text.toString()
        val category = binding.categoryTextField.editText?.text.toString()
        val bio = binding.bioTextField.editText?.text.toString()
        val city = binding.cityTextField.editText?.text.toString()
        val province = binding.provinceTextField.editText?.text.toString()

        val data = mapOf(
            "displayName" to name,
            "category" to category,
            "bio" to bio,
            "city" to city,
            "province" to province
        )


        db.collection("users")
            .document(user.uid)
            .update(data)
            .addOnCompleteListener {
                if (file != null) {
                    editProfileViewModel.uploadAvatar(
                        token.value.toString(),
                        file!!,
                        user.uid,
                        object : ApiCallback {
                            override fun onResponse(success: Boolean) {
                                if (success) {
                                    Intent(
                                        applicationContext,
                                        MainActivity::class.java
                                    ).apply {
                                        startActivity(this)
                                        finishAffinity()
                                    }
                                }
                            }

                        })
                }

                showToast(this, "Profile updated successfully.")
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}