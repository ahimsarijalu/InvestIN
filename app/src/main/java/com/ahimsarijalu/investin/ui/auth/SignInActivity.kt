package com.ahimsarijalu.investin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahimsarijalu.investin.databinding.ActivitySignInBinding
import com.ahimsarijalu.investin.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        setupAction()

    }

    private fun setupAction() {
        binding.signinBtn.setOnClickListener {
            val email = binding.emailTextField.editText?.text.toString()
            val password = binding.passwordTextField.editText?.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        updateUI()
                    } else {
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        binding.signupBtn.setOnClickListener {
            Intent(this, SignupActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.arrowUp.setOnClickListener {
            Intent(this, SignupActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun updateUI() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finishAffinity()
        }
    }
}