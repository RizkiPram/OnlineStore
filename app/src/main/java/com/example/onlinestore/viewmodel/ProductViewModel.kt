package com.example.onlinestore.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinestore.data.ProductDataClass
import com.example.onlinestore.data.remote.api.ApiConfig
import com.example.onlinestore.data.remote.response.BaseResponse
import com.example.onlinestore.data.remote.response.ItemsItem
import com.example.onlinestore.data.remote.response.ProductResponse
import com.example.onlinestore.data.remote.response.VariantsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel:ViewModel() {
    private val _listProduct = MutableLiveData<List<ItemsItem>>()
    val listProduct:MutableLiveData<List<ItemsItem>> = _listProduct

    fun addProduct(header:String,title:String,desc:String,variantsItem: VariantsItem){
        val product=ProductDataClass( title, desc, variantsItem)
        val client = ApiConfig.getApiService().addProduct(header,product)
        client.enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    Log.d(TAG,"OnSuccess : ${response.body()?.message}")
                }else{
                    Log.d(TAG,"OnFailed : ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG,"OnFailure : ${t.message}")
            }
        })
    }
    fun getListProduct(header:String){
        val client = ApiConfig.getApiService().getListProduct(header)
        client.enqueue(object : Callback<ProductResponse>{
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        _listProduct.value=responseBody.data.items
                    }
                }else{
                    Log.d(TAG,"notSuccess : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.d(TAG,"OnFailure:${t.message}")
            }
        })
    }
    companion object {
        const val TAG = "ProductViewModel"
    }
}