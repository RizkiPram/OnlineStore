package com.example.onlinestore.data.remote.api

import com.example.onlinestore.data.remote.response.BaseResponse
import com.example.onlinestore.data.remote.response.LoginResponse
import com.example.onlinestore.data.remote.response.ProductResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<LoginResponse>

    @GET("product")
    @Headers("Authorization:Bearer 243|RQYp7XCuHmHe5tXoft5MJQyvpFiMiGvjcvVIBJ7G31f6c45c")
    fun getListProduct():Call<ProductResponse>
}