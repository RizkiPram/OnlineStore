package com.example.onlinestore.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinestore.data.remote.api.ApiConfig
import com.example.onlinestore.data.remote.response.ItemsItem
import com.example.onlinestore.data.remote.response.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel:ViewModel() {
    private val _listProduct = MutableLiveData<List<ItemsItem>>()
    val listProduct:MutableLiveData<List<ItemsItem>> = _listProduct

    fun getListProduct(){
        val client = ApiConfig.getApiService().getListProduct()
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