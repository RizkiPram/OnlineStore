package com.example.onlinestore.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinestore.R
import com.example.onlinestore.adapter.ProductAdapter
import com.example.onlinestore.data.local.UserPreferences
import com.example.onlinestore.data.remote.response.ItemsItem
import com.example.onlinestore.data.remote.response.VariantsItem
import com.example.onlinestore.databinding.ActivityListProductBinding
import com.example.onlinestore.viewmodel.AuthViewModel
import com.example.onlinestore.viewmodel.ProductViewModel
import com.example.onlinestore.viewmodel.ViewModelFactory
import com.google.android.material.button.MaterialButton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ListProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListProductBinding
    private lateinit var variants:VariantsItem
    private val viewModel by viewModels<ProductViewModel>()
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            imgAddProduct.setOnClickListener { moveToAddProduct() }
            imgAddProductRectangle.setOnClickListener { moveToAddProduct() }
            imgLogout.setOnClickListener { showDialog() }
        }
        setupViewModel()
    }

    private fun setupViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        authViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[AuthViewModel::class.java]
        authViewModel.getUser().observe(this) {
            viewModel.getListProduct(it.token)
        }
//        authViewModel.logout()
        viewModel.listProduct.observe(this) {
            setupListProduct(it)
        }
    }

    private fun setupListProduct(data: List<ItemsItem>) {
        val listProduct = ArrayList<ItemsItem>()
        data.forEach { listProduct.add(it) }
        binding.rvProduct.apply {
            adapter = ProductAdapter(listProduct)
            layoutManager = GridLayoutManager(this@ListProductActivity, 2)
        }
    }

    private fun moveToAddProduct() {
        startActivity(Intent(this, AddProductActivity::class.java))
    }

    private fun showDialog() {
        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_dialog)
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.findViewById<TextView>(R.id.tvDesc).text = "Are You Sure Want to Logout?"
        dialog.findViewById<MaterialButton>(R.id.btnYes)?.setOnClickListener {
            authViewModel.logout()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        dialog.findViewById<MaterialButton>(R.id.btnNo)?.setOnClickListener {
            dialog.cancel()
        }
    }
}
