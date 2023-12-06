package com.example.onlinestore.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("data")
	val data: UserData,

	@field:SerializedName("message")
	val message: String
)

data class UserData(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("token")
	val token: String
)
