package com.example.onlinestore.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.onlinestore.data.local.UserPreferences
import com.example.onlinestore.data.remote.response.VariantsItem
import com.example.onlinestore.databinding.ActivityAddProductBinding
import com.example.onlinestore.utils.rotateBitmap
import com.example.onlinestore.viewmodel.AuthViewModel
import com.example.onlinestore.viewmodel.ProductViewModel
import com.example.onlinestore.viewmodel.ViewModelFactory
import java.io.File

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private val viewModel by viewModels<ProductViewModel>()
    private lateinit var  variants:VariantsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            btnAddVariant.setOnClickListener { startActivity(Intent(this@AddProductActivity,AddVariantActivity::class.java)) }
        }
        setupViewModel()
    }
    private fun setupViewModel(){
        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[AuthViewModel::class.java]
        binding.apply {
            btnAddVariant.setOnClickListener { openActivityForResult() }
            btnSave.setOnClickListener {
                val title = etName.text.toString()
                val desc=etDesc.text.toString()
                authViewModel.getUser().observe(this@AddProductActivity){
                    viewModel.addProduct(it.token,title,desc,variants)
                }
            }
        }
    }
//    private fun startCameraX() {
//        val intent = Intent(this, AddVariantActivity::class.java)
//        launcherIntentCameraX.launch(intent)
//    }
//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERA_X_RESULT) {
//            val myFile = it.data?.getSerializableExtra("picture") as File
//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
////            getFile = myFile
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(getFile?.path),
//                isBackCamera
//            )
////            binding.btnAddVariant.setImageBitmap(result)
////            viewModel.getFileResult(getFile)
//        }
//    }

    private fun openActivityForResult() {
        startForResult.launch(Intent(this, AddVariantActivity::class.java))
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val variantsData=intent?.getParcelableExtra<VariantsItem>(AddVariantActivity.VARIANTS)
                if (variantsData != null) {
                    variants = VariantsItem(
                        variantsData.image,
                        variantsData.price,
                        variantsData.name,
                        variantsData.stock
                    )
                    Log.d("variants",variantsData.toString())
                }
            }
        }



}