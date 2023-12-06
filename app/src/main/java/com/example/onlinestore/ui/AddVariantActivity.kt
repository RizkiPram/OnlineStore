package com.example.onlinestore.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinestore.databinding.ActivityAddVariantBinding

class AddVariantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddVariantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            var stock = 0
            etVariantStock.isEnabled = false
            imgBack.setOnClickListener { onBackPressed() }
            imgAddStock.setOnClickListener {
                stock += 1
                etVariantStock.setText(stock.toString())
            }
            imgMinusStock.setOnClickListener {
                if (stock != 0) {
                    stock -= 1
                }
                etVariantStock.setText(stock.toString())
            }
            btnAddVariant.setOnClickListener { finish() }
        }
    }
}