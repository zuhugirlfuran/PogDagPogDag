package com.ssafy.snuggle_final_app.product

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.cart.CartFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.Comment
import com.ssafy.snuggle_final_app.data.model.dto.Like
import com.ssafy.snuggle_final_app.databinding.FragmentProductDetailBinding
import com.ssafy.snuggle_final_app.main.MainActivityViewModel
import com.ssafy.snuggle_final_app.order.OrderFragment
import com.ssafy.snuggle_final_app.util.CommonUtils
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma
import org.w3c.dom.Text
import java.util.Date

private const val TAG = "ProductDetailFragment"

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(
    FragmentProductDetailBinding::bind,
    R.layout.fragment_product_detail
), ProductDetailAdapter.OnCommentClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var adapter: ProductDetailAdapter
    private val viewModel: ProductDetailFragmentViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 화면 전환 이벤트
//        mainActivity.fragmentBackPressed(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                viewModel.setCheck(true) // 체크 플래그 설정
//                parentFragmentManager.popBackStack() // 이전 Fragment로 이동
//            }
//        })

        // 어댑터 초기화
        initAdapter()

        val productId = viewModel.productId
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId

        if (productId > 0) {
            viewModel.getProductWithComments(productId)
            viewModel.isLikeStatus(userId, productId)
        }

        observeViewModel()
        observeCommentViewModel()

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
            Log.d(TAG, "onViewCreated: 좋아요 버튼")
            val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
            val today = CommonUtils.dateformatYMD(Date())
            Log.d(TAG, "onViewCreated: userId ${userId} productId ${productId}")
            viewModel.likeProduct(Like(userId, productId, today))
        }

        commentViewModel.fetchComments(productId)
    }

    private fun showOrderDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_bottom_order, null)
        bottomSheetDialog.setContentView(dialogView)
        val plusButton = dialogView.findViewById<ImageButton>(R.id.product_detail_btn_plus)
        val minusButton = dialogView.findViewById<ImageButton>(R.id.product_detail_btn_minus)
        val quantityTV = dialogView.findViewById<TextView>(R.id.product_detail_tv_quantity)


        // 다이얼로그의 '구매' 버튼 클릭 시 처리
        val orderButton = dialogView.findViewById<Button>(R.id.product_detail_btn_purchase)

        val productName = dialogView.findViewById<TextView>(R.id.product_detail_tv_title)
        productName.text = binding.productDetailTvName.text

        var quantity = 1
        quantityTV.text = quantity.toString()

        val productPrice = viewModel.productInfo.value?.productPrice ?: 0
        val priceTv = dialogView.findViewById<TextView>(R.id.product_detail_tv_price)

        updateDialogPrice(priceTv, productPrice, quantity)

        plusButton.setOnClickListener {
            quantity++
            quantityTV.text = quantity.toString()
            updateDialogPrice(priceTv, productPrice, quantity)
        }

        minusButton.setOnClickListener {
            if (quantity > 1) quantity--
            quantityTV.text = quantity.toString()
            updateDialogPrice(priceTv, productPrice, quantity)
        }


        orderButton.setOnClickListener {
            bottomSheetDialog.dismiss()
            addItemToCart(quantity)
            mainActivity.addToStackFragment(OrderFragment())
        }

        // 다이얼로그의 '장바구니' 버튼 클릭 시 처리
        val cartBtn = dialogView.findViewById<Button>(R.id.product_detail_btn_cart)
        cartBtn.setOnClickListener {
            bottomSheetDialog.dismiss()
            addItemToCart(quantity)
            Toast.makeText(requireContext(), "상품이 장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
        }

        bottomSheetDialog.show()
    }

    private fun updateDialogPrice(priceTV: TextView, unitPrice: Int, quantity: Int) {
        val totalPrice = unitPrice * quantity
        priceTV.text = "${makeComma(totalPrice)}"
    }

    private fun addItemToCart(quantity: Int) {
        viewModel.productInfo.value?.let { product ->
            val cartItem = Cart(
                productId = product.productId, // 추가된 productId 전달
                img = product.productImg,
                title = product.productName,
                productCnt = quantity,
                price = product.productPrice,
                deliveryDate = "2024-12-25T12:23:15" // 샘플 배송 날짜
            )
            Log.d("CartCreation", "Created Cart Item: Product ID=${cartItem.productId}, Title=${cartItem.title}")
            activityViewModel.addShoppingList(cartItem)
        }
    }

    private fun showCommentDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_add_comment, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()

        val dialogButton = dialogView.findViewById<Button>(R.id.dialog_button)
        val dialogInput = dialogView.findViewById<EditText>(R.id.comment_dialog_input)

        dialogButton.setOnClickListener {  // 확인 버튼
            val inputText = dialogInput.text.toString()
            if (inputText.isNotEmpty()) {
                val productId = viewModel.productId
                val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
                val newComment = Comment(
                    comment = inputText,
                    productId = productId,
                    userId = userId
                )

                commentViewModel.addComment(newComment)
                dialog.dismiss()
            } else {
                showToast("댓글을 입력해주세요.")
            }
        }
        dialog.show()
    }

    private fun initAdapter() {

        adapter = ProductDetailAdapter(emptyList(), this)

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
                    binding.productDetailTvPrice.text = makeComma(it.productPrice)

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

    private fun observeCommentViewModel() {
        // 댓글 기능
        commentViewModel.comments.observe(viewLifecycleOwner) { comments ->
            Log.d(TAG, "observeCommentViewModel: ${comments}")
            adapter.submitList(comments)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    override fun onUpdateClick(comment: Comment, position: Int, updatedComment: String) {

        if (comment.commentId > 0) {
            val updated = comment.copy(comment = updatedComment)
            Log.d(TAG, "onUpdateClick: $updated $position ${comment.commentId}")
            commentViewModel.updateComment(updated)
            adapter.notifyItemChanged(position) // position 값을 사용
        }

    }


    override fun onDeleteClick(comment: Comment, position: Int) {
        commentViewModel.deleteComment(comment.commentId)
        adapter.notifyItemRemoved(position)
    }

    override fun onInsertClick(comment: Comment, position: Int) {
        commentViewModel.addComment(comment)
        adapter.notifyItemInserted(0)
        // RecyclerView를 가장 위로 스크롤
        binding.productDetailRecyclerview.scrollToPosition(0)
    }

//    private fun showDeleteDialog(comment: Comment, onCommentDeleted: (Comment) -> Unit) {
//        // 다이얼로그 또는 다른 UI로 수정 입력받기
//        val dialog = AlertDialog.Builder(requireContext())
//            .setTitle("댓글을 삭제하시겠습니까?")
////            .setView(R.layout.dialog_edit_comment) // 레이아웃 추가 필요
//            .setPositiveButton("삭제") { _, _ ->
//                val updatedComment = comment.copy(comment = "새로운 댓글 내용") // 입력값 가져와서 수정
//                onCommentUpdated(updatedComment)
//            }
//            .setNegativeButton("취소", null)
//            .create()
//
//        dialog.show()
//    }

}