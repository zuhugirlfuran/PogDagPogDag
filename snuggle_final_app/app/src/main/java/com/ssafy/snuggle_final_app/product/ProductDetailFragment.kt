package com.ssafy.snuggle_final_app.product

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.cart.CartFragment
import com.ssafy.snuggle_final_app.data.model.dto.Like
import com.ssafy.snuggle_final_app.databinding.FragmentProductDetailBinding
import com.ssafy.snuggle_final_app.order.OrderFragment
import com.ssafy.snuggle_final_app.util.CommonUtils
import java.util.Date

private const val TAG = "ProductDetailFragment"

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(
    FragmentProductDetailBinding::bind,
    R.layout.fragment_product_detail
) {

    private lateinit var adapter: ProductDetailAdapter
    private val viewModel: ProductDetailFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        val productId = viewModel.productId
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId

        if (productId > 0) {
            viewModel.getProductWithComments(productId)
            viewModel.isLikeSatatus(userId, productId)
        }

        observeViewModel()

        // 댓글달기 버튼
        binding.productDetailBtnComment.setOnClickListener {
            showCommentDialog()
        }

        // 주문하기 버튼
        binding.productDetailBtnOrder.setOnClickListener {
            showOrderDialog()
        }

        // 좋아요 버튼
        binding.productDetailBtnLike.setOnClickListener {
            val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
            val today = CommonUtils.dateformatYMD(Date())
            viewModel.likeProduct(Like(userId, productId, today))
        }

    }

    private fun showOrderDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_bottom_order, null)
        bottomSheetDialog.setContentView(dialogView)

        // 다이얼로그의 '구매' 버튼 클릭 시 처리
        val orderButton = dialogView.findViewById<Button>(R.id.product_detail_btn_purchase)
        orderButton.setOnClickListener {
            // 다이얼로그 종료
            bottomSheetDialog.dismiss()

            // OrderFragment로 이동
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, OrderFragment()) // `R.id.main_frameLayout`은 프래그먼트 교체할 컨테이너 ID
            transaction.addToBackStack(null)
            transaction.commit()
        }
        
        // 다이얼로그의 '장바구니' 버튼 클릭 시 처리
        val cartBtn = dialogView.findViewById<Button>(R.id.product_detail_btn_cart)
        cartBtn.setOnClickListener {
            bottomSheetDialog.dismiss()

            // OrderFragment로 이동
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, CartFragment()) // `R.id.main_frameLayout`은 프래그먼트 교체할 컨테이너 ID
            transaction.addToBackStack(null)
            transaction.commit()
        }

        bottomSheetDialog.show()
    }

    private fun showCommentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_comment, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()

        val dialogButton = dialogView.findViewById<Button>(R.id.dialog_button)
        dialogButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun initAdapter() {
//        val commentList = mutableListOf(
//            Comment("너무 귀여워요!", 1, "id 01"),
//            Comment("키링으로 활용하기 좋아요", 1, "id 01"),
//            Comment("아기자기하고 어쩌구 저쩌구", 1, "id 01"),
//            Comment("색 커스텀이 어쩌구 ", 1, "id 01")
//        )

        adapter = ProductDetailAdapter(emptyList())

        binding.productDetailRecyclerview.adapter = adapter
        binding.productDetailRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        // 상품 정보 불러오기
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->

            Log.d(TAG, "observeViewModel: $productInfo")
            productInfo?.let {
                binding.apply {
                    // 상품 정보 업데이트
                    Glide.with(binding.productDetailIvImg)
                        .load(it.productImg)
                        .into(binding.productDetailIvImg)

                    binding.productDetailTvName.text = it.productName
                    binding.productDetailTvLikecount.text = it.likeCount.toString()
                    binding.productDetailTvContent.text = it.content
                    binding.productDetailTvPrice.text = "${it.productPrice}원"

                    // 댓글 RecyclerView 업데이트
                    adapter.submitList(it.comments)
                }
            }
        }

        // 좋아요 눌렀을 때
        viewModel.isProductLiked.observe(viewLifecycleOwner) { isLiked ->

            val likeCount = viewModel.productInfo.value?.likeCount ?: 0
            binding.productDetailTvLikecount.text = likeCount.toString() // likeCount 업데이트
            Log.d(TAG, "observ: ${likeCount}")
            if (isLiked) {
                binding.productDetailBtnLike.setImageResource(R.drawable.heart_fill)
            } else {
                binding.productDetailBtnLike.setImageResource(R.drawable.heart)
            }
        }
    }

}