package com.ssafy.snuggle_final_app.product

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.databinding.ItemProductListBinding

private const val TAG = "ProductAdapter"

class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductListBinding) :
        ViewHolder(binding.root) {
        fun bind(product: Product, onItemClick: (Int) -> Unit) {

            Log.d(TAG, "bind: ${product.image}")
            val requestOptions = RequestOptions().transform(RoundedCorners(50))// 둥글기 설정
            Glide.with(binding.productIv)
                .load(product.image) // 리소스 또는 URL
                .apply(requestOptions)
                .into(binding.productIv)

            binding.productTvName.text = product.productName
            binding.productTvPrice.text = "${product.price}원"

            // 클릭 이벤트 처리
            binding.root.setOnClickListener { onItemClick(product.productId) }
        }
    }

    fun submitList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], onItemClick)
    }


}