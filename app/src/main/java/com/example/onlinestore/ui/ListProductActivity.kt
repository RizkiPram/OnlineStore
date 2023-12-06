package com.example.onlinestore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinestore.R
import com.example.onlinestore.adapter.ProductAdapter
import com.example.onlinestore.data.remote.response.ItemsItem
import com.example.onlinestore.databinding.ActivityListProductBinding
import com.example.onlinestore.viewmodel.ProductViewModel

class ListProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListProductBinding
    private val viewModel by viewModels<ProductViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            imgAddProduct.setOnClickListener { moveToAddProduct() }
            imgAddProductRectangle.setOnClickListener { moveToAddProduct() }
        }
        viewModel.getListProduct()
        viewModel.listProduct.observe(this){
            setupListProduct(it)
        }
    }
    private fun setupListProduct(data:List<ItemsItem>){
        val listProduct = ArrayList<ItemsItem>()
        data.forEach { listProduct.add(it) }
        binding.rvProduct.apply {
            adapter = ProductAdapter(listProduct)
            layoutManager=GridLayoutManager(this@ListProductActivity,2)
        }
    }
    private fun moveToAddProduct(){
        startActivity(Intent(this,AddProductActivity::class.java))
    }
}