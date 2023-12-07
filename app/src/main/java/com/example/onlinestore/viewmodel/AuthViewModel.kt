package com.example.onlinestore.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.data.local.UserModel
import com.example.onlinestore.data.local.UserPreferences
import com.example.onlinestore.data.remote.api.ApiConfig
import com.example.onlinestore.data.remote.response.BaseResponse
import com.example.onlinestore.data.remote.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val preferences: UserPreferences) : ViewModel() {


    fun register(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "success : ${response.message()}")
                } else {
                    Log.d(TAG, "NotSuccess : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun login(email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        val userModel = UserModel(
                            responseBody.data.id,
                            responseBody.data.token,
                            true
                        )
                        saveUser(userModel)
                        Log.d(TAG, "success : $userModel")
                    }
                } else {
                    Log.d(TAG, "notSuccess : ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "OnFailure : ${t.message}")
            }
        })
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            preferences.saveUser(user)
        }
    }
    fun getUser(): LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }
    fun logout(){
        viewModelScope.launch {
            preferences.logout()
        }
    }

    companion object {
        const val TAG = "AuthViewModel"
    }
}