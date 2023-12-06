package com.example.onlinestore.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinestore.data.remote.api.ApiConfig
import com.example.onlinestore.data.remote.response.BaseResponse
import com.example.onlinestore.data.remote.response.LoginResponse
import com.example.onlinestore.data.remote.response.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel:ViewModel() {
    private val _login = MutableLiveData<UserData>()
    val login:MutableLiveData<UserData> = _login

    fun register(name:String,email:String,password: String){
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    Log.d(TAG,"success : ${response.message()}")
                }else{
                    Log.d(TAG,"NotSuccess : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG,"onFailure : ${t.message}")
            }
        })
    }
    fun login(email:String,password:String){
        val client=ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        _login.value=responseBody.data
                        Log.d(TAG,"success : ${responseBody.message}")
                    }
                }else{
                    Log.d(TAG,"notSuccess : ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG,"OnFailure : ${t.message}")
            }
        })
    }
    companion object{
        const val TAG = "AuthViewModel"
    }
}