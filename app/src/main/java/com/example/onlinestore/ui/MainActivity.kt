package com.example.onlinestore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinestore.R
import com.example.onlinestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnMoveToLogin.setOnClickListener { startActivity(Intent(this@MainActivity,LoginActivity::class.java)) }
            btnMoveToRegister.setOnClickListener { startActivity(Intent(this@MainActivity,RegisterActivity::class.java)) }
        }
    }
}