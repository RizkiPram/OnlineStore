package com.example.onlinestore.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinestore.databinding.ActivityLoginBinding
import com.example.onlinestore.utils.isValidEmail
import com.example.onlinestore.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            btnLogin.setOnClickListener {
            val email=etEmail.text.toString()
            val password=etPassword.text.toString()

                if (!email.isValidEmail()){
                    textLayoutEmail.error="Email Tidak Valid"
                }
                if (email.isValidEmail()){
                    viewModel.login(email, password)
                    startActivity(Intent(this@LoginActivity,ListProductActivity::class.java))
                }
            }
        }
    }
}