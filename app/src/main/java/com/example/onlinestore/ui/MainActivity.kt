package com.example.onlinestore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestore.data.local.UserPreferences
import com.example.onlinestore.databinding.ActivityMainBinding
import com.example.onlinestore.viewmodel.AuthViewModel
import com.example.onlinestore.viewmodel.ViewModelFactory


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
        setupViewModel()
    }
    private fun setupViewModel(){
        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[AuthViewModel::class.java]
        authViewModel.getUser().observe(this){
            if (it.isLogin){
                startActivity(Intent(this@MainActivity,ListProductActivity::class.java))
                finish()
            }
        }
    }
}