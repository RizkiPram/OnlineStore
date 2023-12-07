package com.example.onlinestore.data

import com.example.onlinestore.data.remote.response.VariantsItem

data class ProductDataClass(
//    var header:String,
    var title:String,
    var description:String,
    var variants: VariantsItem
)
