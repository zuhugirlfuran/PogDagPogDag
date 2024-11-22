package com.ssafy.snuggle_final_app.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssafy.snuggle_final_app.databinding.ItemProductDetailListBinding
import com.ssafy.snuggle_final_app.data.model.dto.Comment

class ProductDetailAdapter
    (private var commentList: List<Comment>) :
    RecyclerView.Adapter<ProductDetailAdapter.ProductDetailViewHolder>() {

    class ProductDetailViewHolder(private val binding: ItemProductDetailListBinding) :
        ViewHolder(binding.root) {
        fun bind(comment: Comment) {

            binding.commentTvContent.text = comment.comment
            
            // 수정 버튼 클릭 시
            binding.commentIbModify.setOnClickListener { 
                binding.commentEtContent.visibility = View.VISIBLE
                binding.commentTvContent.visibility = View.GONE

                binding.commentEtContent.setText(binding.commentTvContent.text)
            }

            // 삭제 버튼 클릭 시
            binding.commentIbDelete.setOnClickListener {

            }

        }
    }

    fun submitList(list: List<Comment>) {
        commentList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailViewHolder {
        val binding = ItemProductDetailListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductDetailViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: ProductDetailViewHolder,
        position: Int
    ) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int = commentList.size
}