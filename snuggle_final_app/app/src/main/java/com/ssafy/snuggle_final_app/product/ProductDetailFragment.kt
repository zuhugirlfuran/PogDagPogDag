package com.ssafy.snuggle_final_app.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentProductDetailBinding

private const val TAG = "ProductDetailFragment"

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductDetailAdapter
    private val viewModel: ProductDetailFragmentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        val productId = viewModel.productId

        if (productId > 0) {
            viewModel.getProductWithComments(productId)
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

    }

    private fun showOrderDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_bottom_order, null)
        bottomSheetDialog.setContentView(dialogView)
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
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->

            Log.d(TAG, "observeViewModel: $productInfo")
            productInfo?.let {
                binding.apply {
                    // 상품 정보 업데이트
                    Glide.with(binding.productDetailIvImg)
                        .load(it.productImg)
                        .into(binding.productDetailIvImg)

                    Log.d(TAG, "observeViewModel: $it")
                    binding.productDetailTvName.text = it.productName
                    binding.productDetailTvLikecount.text = it.likeCount.toString()
                    binding.productDetailTvContent.text = it.content
                    binding.productDetailTvPrice.text = "${it.productPrice}원"

                    // 댓글 RecyclerView 업데이트
                    adapter.submitList(it.comments)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}