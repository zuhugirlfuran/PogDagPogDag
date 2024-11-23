package com.ssafy.snuggle_final_app.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.databinding.MainHorizontalRecyclerItemBinding
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma

private const val TAG = "BestProductRecyclerView"

class BestProductRecyclerViewAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<BestProductRecyclerViewAdapter.BestProductViewHolder>() {

    class BestProductViewHolder(private val binding: MainHorizontalRecyclerItemBinding) :
        ViewHolder(binding.root) {

        fun bind(product: Product, onItemClick: (Int) -> Unit) {

            Log.d(TAG, "bind: ${product.image}")
            val requestOptions = RequestOptions().transform(RoundedCorners(50))// 둥글기 설정
            Glide.with(binding.itemImage)
                .load(product.image) // 리소스 또는 URL
                .apply(requestOptions)
                .into(binding.itemImage)

            binding.itemName.text = product.productName
            binding.itemPrice.text = "${makeComma(product.price)}"


            // 클릭 이벤트 처리
            binding.root.setOnClickListener { onItemClick(product.productId) }
        }
    }

    fun submitList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductViewHolder {
        val binding = MainHorizontalRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BestProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestProductViewHolder, position: Int) {
        holder.bind(productList[position], onItemClick)
    }

    override fun getItemCount(): Int = productList.size

}
