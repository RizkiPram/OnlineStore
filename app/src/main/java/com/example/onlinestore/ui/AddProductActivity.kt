package com.example.onlinestore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinestore.R
import com.example.onlinestore.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            btnAddVariant.setOnClickListener { startActivity(Intent(this@AddProductActivity,AddVariantActivity::class.java)) }
            btnSave.setOnClickListener { finish() }
        }
    }
}