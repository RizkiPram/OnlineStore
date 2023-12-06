package com.example.onlinestore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinestore.R
import com.example.onlinestore.databinding.ActivityListProductBinding

class ListProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            imgAddProduct.setOnClickListener { moveToAddProduct() }
            imgAddProductRectangle.setOnClickListener { moveToAddProduct() }
        }
    }
    private fun moveToAddProduct(){
        startActivity(Intent(this,AddProductActivity::class.java))
    }
}