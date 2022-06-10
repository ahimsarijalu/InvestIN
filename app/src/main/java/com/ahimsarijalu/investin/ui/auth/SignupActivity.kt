package com.ahimsarijalu.investin.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        val roles = this.getSharedPreferences("isInvestor", Context.MODE_PRIVATE)
            .getBoolean("isInvestor", true)
        setupAction(roles)
        setupView()
    }

    private fun setupView() {
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

    private fun setupAction(roles: Boolean) {
        binding.signupBtn.setOnClickListener {
            val email = binding.emailTextField.editText?.text.toString()
            val password = binding.passwordTextField.editText?.text.toString()
            val category = binding.categoryTextField.editText?.text.toString()
            val name = binding.nameTextField.editText?.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.getIdToken(true)
                        val db = Firebase.firestore

                        val userDetails = mapOf(
                            "investorRole" to roles,
                            "category" to category,
                            "displayName" to name
                        )

                        db.collection("users")
                            .document(user?.uid.toString())
                            .update(userDetails)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this, "Account created",
                                    Toast.LENGTH_SHORT
                                ).show()
                                auth.signOut()
                                Intent(this, SignInActivity::class.java).apply {
                                    startActivity(this)
                                    finish()
                                }
                            }

                    } else {
                        Toast.makeText(
                            this, task.exception?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


    companion object {
        const val TAG = "SignupActivity"
    }
}