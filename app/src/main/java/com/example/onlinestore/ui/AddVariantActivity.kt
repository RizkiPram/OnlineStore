package com.example.onlinestore.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.onlinestore.data.remote.response.VariantsItem
import com.example.onlinestore.databinding.ActivityAddVariantBinding
import com.example.onlinestore.utils.rotateBitmap
import com.example.onlinestore.utils.uriToFile
import java.io.File

class AddVariantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddVariantBinding
    private var getFile: File? = null
    private var image:String=""
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
            imgVariant.setOnClickListener { startGallery() }
            btnAddVariant.setOnClickListener {
                val name=etVariantName.text.toString()
                val price=etVariantPrice.text.toString()
                val stock = etVariantStock.text.toString()
                val variantsItem = VariantsItem(
                    image,
                    price.toInt(),
                    name,
                    stock.toInt()
                )
                val intent = Intent()
                intent.putExtra(VARIANTS,variantsItem)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )
            binding.imgVariant.setImageBitmap(result)
            image= getFile?.path.toString()
//            viewModel.getFileResult(getFile)
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@AddVariantActivity)
            val byteArray:ByteArray = uriToFile(selectedImg,this@AddVariantActivity)
            val base64Encode = Base64.encodeToString(byteArray,Base64.DEFAULT)
//            getFile = myFile
            binding.imgVariant.setImageURI(selectedImg)
            image= base64Encode
//            viewModel.getFileResult(getFile)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    companion object{
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val VARIANTS="variants"
    }
}