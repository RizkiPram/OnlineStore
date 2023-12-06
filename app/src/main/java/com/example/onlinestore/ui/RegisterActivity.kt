package com.example.onlinestore.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinestore.databinding.ActivityRegisterBinding
import com.example.onlinestore.utils.isValidEmail
import com.example.onlinestore.utils.isValidPassword
import com.example.onlinestore.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            btnRegister.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etPasswordConfirm.text.toString()
                if (!email.isValidEmail()) {
                    textLayoutEmail.error = "Email Tidak Valid"
                } else {
                    textLayoutEmail.error = null
                }
                if (name.isEmpty()) {
                    textLayoutName.error = " Nama Tidak Boleh Kosong"
                } else {
                    textLayoutName.error = null
                }
                if (!isValidPassword(password)) {
                    textLayoutPassword.error = "Password harus mengandung 1 Kapital dan Symbol"
                } else {
                    textLayoutPassword.error = null
                }
                if (password != confirmPassword) {
                    textLayoutPassword.error = "Password Dan Konfirmasi Password tidak sama"
                    textLayoutPasswordConfirm.error = "Password Dan Konfirmasi Password tidak sama"
                } else {
                    textLayoutPassword.error = null
                    textLayoutPasswordConfirm.error = null
                }
                if (name.isNotEmpty() && email.isValidEmail() && isValidPassword(password)) {
                    viewModel.register(name, email, password)
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }

            }
            imgBack.setOnClickListener { onBackPressed() }
        }
    }
}