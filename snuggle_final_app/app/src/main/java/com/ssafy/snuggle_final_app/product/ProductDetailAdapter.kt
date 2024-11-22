package com.ssafy.snuggle_final_app.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.databinding.ItemProductDetailListBinding
import com.ssafy.snuggle_final_app.data.model.dto.Comment

class ProductDetailAdapter
    (
    private var commentList: List<Comment>,
    private val listener: OnCommentClickListener
) :
    RecyclerView.Adapter<ProductDetailAdapter.ProductDetailViewHolder>() {

    interface OnCommentClickListener {
        fun onInsertClick(comment: Comment, position: Int)
        fun onDeleteClick(comment: Comment, position: Int)
        fun onUpdateClick(comment: Comment, position: Int, updatedContent: String)
    }

    inner class ProductDetailViewHolder(private val binding: ItemProductDetailListBinding) :
        ViewHolder(binding.root) {
        fun bind(comment: Comment) {

            binding.commentTvContent.text = comment.comment
            val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
            if (userId != comment.userId) {
                binding.commentIbModify.visibility = View.GONE
                binding.commentIbDelete.visibility = View.GONE
            }

            // 수정 버튼 클릭 시
            binding.commentIbModify.setOnClickListener {
                binding.commentEtContent.visibility = View.VISIBLE  // 수정 et 화면
                binding.commentTvContent.visibility = View.GONE  // 원래 댓글 화면
                binding.commentIbRegister.visibility = View.VISIBLE  // 댓글 확인 버튼 보이게
                binding.commentIbModify.visibility = View.GONE  // 수정 버튼 안보이게
                binding.commentEtContent.setText(binding.commentTvContent.text)  // et에 값 세팅

            }
            // 삭제 버튼 클릭 시
            binding.commentIbDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(comment, position)
                }
            }

            // 확인 버튼 클릭 시
            binding.commentIbRegister.setOnClickListener {
                val updatedComment = binding.commentEtContent.text.toString()
                binding.commentTvContent.text = updatedComment // UI 업데이트
                binding.commentEtContent.visibility = View.GONE  // 수정 et 화면 숨김
                binding.commentTvContent.visibility = View.VISIBLE  // 원래 댓글 화면 보임
                binding.commentIbRegister.visibility = View.GONE  // 댓글 확인 버튼 숨김
                binding.commentIbModify.visibility = View.VISIBLE  // 수정 버튼 보임

                // 리스트에 업데이트 반영
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onUpdateClick(comment, adapterPosition, updatedComment)
                }
            }
        }
    }

    fun submitList(list: List<Comment>) {
        commentList = listOf()
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