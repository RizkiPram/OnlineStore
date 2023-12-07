package com.example.onlinestore.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestore.data.local.UserPreferences
import com.example.onlinestore.databinding.ActivityLoginBinding
import com.example.onlinestore.utils.isValidEmail
import com.example.onlinestore.viewmodel.AuthViewModel
import com.example.onlinestore.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
        }
        setupViewModel()
    }
    private fun setupViewModel(){
        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[AuthViewModel::class.java]
        binding.apply { btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (!email.isValidEmail()) {
                textLayoutEmail.error = "Email Tidak Valid"
            }
            if (email.isValidEmail()) {
                authViewModel.login(email, password)
                startActivity(Intent(this@LoginActivity,ListProductActivity::class.java))
                finish()
            }
        }

        }
    }
}