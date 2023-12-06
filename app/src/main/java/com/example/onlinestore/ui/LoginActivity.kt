package com.example.onlinestore.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinestore.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            btnLogin.setOnClickListener { startActivity(Intent(this@LoginActivity,ListProductActivity::class.java)) }
        }
    }
}