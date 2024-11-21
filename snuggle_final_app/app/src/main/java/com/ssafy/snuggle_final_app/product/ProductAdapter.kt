package com.ssafy.snuggle_final_app.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ssafy.snuggle_final_app.dto.Product
import com.ssafy.snuggle_final_app.databinding.ItemProductListBinding

class ProductAdapter(
    private val productList: MutableList<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ItemProductListBinding) :
        ViewHolder(binding.root) {
        fun bind(product: Product , onItemClick: (Product) -> Unit) {

            val requestOptions = RequestOptions().transform(RoundedCorners(50))// 둥글기 설정
            Glide.with(binding.productIv)
                .load(product.imageResId) // 리소스 또는 URL
                .apply(requestOptions)
                .into(binding.productIv)

            // binding.productIv.setImageResource(product.imageResId)
            binding.productTvName.text = product.name
            binding.productTvPrice.text = "${product.price}원"

            // 클릭 이벤트 처리
            binding.root.setOnClickListener { onItemClick(product) }
        }
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