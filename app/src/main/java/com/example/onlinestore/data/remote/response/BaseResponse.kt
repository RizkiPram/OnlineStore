package com.example.onlinestore.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("message")
	val message: String
)
