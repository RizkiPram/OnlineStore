package com.example.onlinestore.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("next_page")
	val nextPage: Any,

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("last_page")
	val lastPage: String,

	@field:SerializedName("items")
	val items: List<ItemsItem>,

	@field:SerializedName("prev_page")
	val prevPage: Any
)

data class ItemsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("variants")
	val variants: List<VariantsItem>,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("total_variant")
	val totalVariant: Int,

	@field:SerializedName("total_stok")
	val totalStok: Int
)

@Parcelize
data class VariantsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

//	@field:SerializedName("id")
//	val id: Int,

	@field:SerializedName("stock")
	val stock: Int
) : Parcelable
