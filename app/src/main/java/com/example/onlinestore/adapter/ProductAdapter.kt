package com.example.onlinestore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinestore.data.remote.response.ItemsItem
import com.example.onlinestore.databinding.ItemProductBinding

class ProductAdapter (private val data:List<ItemsItem>):RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun itemBind(data:ItemsItem){
            binding.apply {
                tvProductName.text=data.title
                tvProductPrice.text="Rp ${data.price} (Harga Terendah)"
                tvProductTotalStock.text="Total Stock : ${data.totalStok}"
                tvProductTotalVariants.text="Total Variant : ${data.totalVariant}"
                Glide.with(itemView)
                    .load(data.image)
                    .into(imgProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(data[position])
    }
}