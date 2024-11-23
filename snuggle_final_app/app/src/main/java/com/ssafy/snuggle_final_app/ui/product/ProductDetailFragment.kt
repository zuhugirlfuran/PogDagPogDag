package com.ssafy.snuggle_final_app.ui.product

import OrderViewModel
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.Comment
import com.ssafy.snuggle_final_app.data.model.dto.Like
import com.ssafy.snuggle_final_app.databinding.FragmentProductDetailBinding

import com.ssafy.snuggle_final_app.ui.order.OrderFragment
import com.ssafy.snuggle_final_app.util.CommonUtils
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma
import java.util.Date

private const val TAG = "ProductDetailFragment"

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(
    FragmentProductDetailBinding::bind,
    R.layout.fragment_product_detail
), ProductDetailAdapter.OnCommentClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var adapter: ProductDetailAdapter
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val productDetailViewModel: ProductDetailFragmentViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 어댑터 초기화
        initAdapter()

        val productId = productDetailViewModel.productId
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId

        if (productId > 0) {
            productDetailViewModel.getProductWithComments(productId)
            productDetailViewModel.isLikeStatus(userId, productId)
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
            productDetailViewModel.likeProduct(Like(userId, productId, today))
        }

        commentViewModel.fetchComments(productId)
    }

    // 주문하기 다이얼로그
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showOrderDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_bottom_order, null)
        bottomSheetDialog.setContentView(dialogView)

        val plusButton = dialogView.findViewById<ImageButton>(R.id.product_detail_btn_plus)
        val minusButton = dialogView.findViewById<ImageButton>(R.id.product_detail_btn_minus)
        val quantityTV = dialogView.findViewById<TextView>(R.id.product_detail_tv_quantity)  // 수량
        val priceTv = dialogView.findViewById<TextView>(R.id.product_detail_tv_price)  // 가격
        val productName = dialogView.findViewById<TextView>(R.id.product_detail_tv_title)  // 상품 이름

        // 초기값
        productName.text = binding.productDetailTvName.text
        var quantity = 1
        quantityTV.text = quantity.toString()
        val productPrice = binding.productDetailTvPrice.text.toString()
        val price = productPrice.filter { it.isDigit() }.toInt()
        Log.d(TAG, "상품 가격 다이얼로그: $productPrice")

        // 다이얼로그 정보 업데이트
        updateDialogPrice(priceTv, price, quantity)

        // + 버튼
        plusButton.setOnClickListener {
            quantity++
            quantityTV.text = quantity.toString()
            updateDialogPrice(priceTv, price, quantity)
        }

        // - 버튼
        minusButton.setOnClickListener {
            if (quantity > 1) quantity--
            quantityTV.text = quantity.toString()
            updateDialogPrice(priceTv, price, quantity)
        }

        // 다이얼로그 '바로 구매' 버튼
        val orderButton = dialogView.findViewById<Button>(R.id.product_detail_btn_purchase)
        orderButton.setOnClickListener {
            bottomSheetDialog.dismiss()
            addItemToCart(quantity)  // 상품에 추가
            mainActivity.addToStackFragment(OrderFragment())  // 주문하기 페이지로 이동
        }

        // 다이얼로그의 '장바구니' 버튼 클릭 시 처리
        val cartBtn = dialogView.findViewById<Button>(R.id.product_detail_btn_cart)
        cartBtn.setOnClickListener {
            bottomSheetDialog.dismiss()
            addItemToCart(quantity)   // 장바구니 페이지로 상품 수량 전달
            Toast.makeText(requireContext(), "상품이 장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
        }

        bottomSheetDialog.show()
    }

    // 다이얼로그 가격 업데이트
    private fun updateDialogPrice(priceTV: TextView, unitPrice: Int, quantity: Int) {
        val totalPrice = unitPrice * quantity
        priceTV.text = makeComma(totalPrice)
    }

    // cart로 아이템 이동
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addItemToCart(quantity: Int) {
        val productId = productDetailViewModel.productId // ViewModel에서 현재 선택된 productId를 가져옴
        productDetailViewModel.productInfo.value.let { item ->
            item?.let {
                Cart(
                    productId = productId, // productId를 직접 설정
                    img = item.productImg,
                    title = it.productName,
                    productCnt = quantity,
                    price = item.productPrice,
                    deliveryDate = "" // 샘플 배송 날짜
                ).apply {
                    Log.d(
                        "CartCreation",
                        "Created Cart Item: Product ID=${this.productId}, Title=${this.title}"
                    )
                    orderViewModel.addShoppingList(this)
                    Toast.makeText(requireContext(), "상품이 장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /** 댓글 추가 다이얼로그 **/
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
                val productId = productDetailViewModel.productId
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

    /** 상품 상세 데이터 불러오기 **/
    private fun initAdapter() {

        adapter = ProductDetailAdapter(emptyList(), this)

        binding.productDetailRecyclerview.adapter = adapter
        binding.productDetailRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    /** 상품 상세 데이터 감시하기 위한 viewmodel observe **/
    private fun observeViewModel() {
        // 상품 정보 불러오기
        productDetailViewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->

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
        productDetailViewModel.isProductLiked.observe(viewLifecycleOwner) { isLiked ->

            val likeCount = productDetailViewModel.productInfo.value?.likeCount ?: 0
            binding.productDetailTvLikecount.text = likeCount.toString() // likeCount 업데이트
            Log.d(TAG, "observ: ${likeCount}")
            if (isLiked) {
                binding.productDetailBtnLike.setImageResource(R.drawable.heart_fill)
            } else {
                binding.productDetailBtnLike.setImageResource(R.drawable.heart)
            }
        }
    }

    /** 댓글 리스트 상태 감시를 위한 옵저버 **/
    private fun observeCommentViewModel() {
        // 댓글 기능
        commentViewModel.comments.observe(viewLifecycleOwner) { comments ->
            Log.d(TAG, "observeCommentViewModel: ${comments}")
            adapter.submitList(comments)
        }
    }

    /** 댓글 상태 변화감지를 위한 click 리스너 **/
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
        adapter.notifyItemInserted(position)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        productDetailViewModel.clearData()
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