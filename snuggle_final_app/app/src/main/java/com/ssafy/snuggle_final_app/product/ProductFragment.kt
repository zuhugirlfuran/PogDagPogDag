package com.ssafy.snuggle_final_app.product

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.databinding.FragmentProductBinding
import com.ssafy.snuggle_final_app.data.service.ProductService
import com.ssafy.snuggle_final_app.databinding.FragmentMainBinding
import com.ssafy.snuggle_final_app.databinding.FragmentProductDetailBinding
import kotlinx.coroutines.launch

class ProductFragment : BaseFragment<FragmentProductBinding>(
    FragmentProductBinding::bind,
    R.layout.fragment_product
) {

    private lateinit var adapter: ProductAdapter
    private lateinit var mainActivity: MainActivity

    private val viewModel: ProductDetailFragmentViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initAdapter()

        // livedata 통해서 관찰하고 리사이클러뷰 업데이트
        observeViewModel()

        // 상품 리스트 데이터 가져오기
        viewModel.getProductList()

        // 서버에 카테고리 연결해 출력하는 어댑터
        // categorySpinnerAdapter(1)
    }

    private fun observeViewModel() {

        // 전체 상품 불러오기
        viewModel.productList.observe(viewLifecycleOwner) { products ->
            if (products.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "상품 목록이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                adapter.submitList(products)
            }
        }

        // 상품 id에 따른 info 불러오기
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->
            productInfo?.let {
                mainActivity.addToStackFragment(ProductDetailFragment())
            }
        }
    }

    private fun initAdapter() {

        adapter = ProductAdapter(emptyList()) { productId ->
            viewModel.productId = productId

            mainActivity.addToStackFragment(ProductDetailFragment())
        }

        binding.productRecyclerview.adapter = adapter
        binding.productRecyclerview.layoutManager = GridLayoutManager(requireContext(), 3)

    }

//    private fun categorySpinnerAdapter(cId: Int) {
//        val categoryService = ApplicationClass.retrofit.create(ProductService::class.java)
//
//        // CoroutineScope를 활용하여 데이터 가져오기
//        lifecycleScope.launch {
//            try {
//                val response = categoryService.getCategory(cId)
//
//                if (response.isSuccessful) {
//                    val category = response.body()
//                    if (category != null) {
//                        val categoryNames = listOf(category.name)
//
//                        // Spinner 어댑터 설정
//                        val spinnerAdapter = ArrayAdapter(
//                            requireContext(),
//                            android.R.layout.simple_spinner_dropdown_item,
//                            categoryNames
//                        )
//                        binding.productDropdownCategory.adapter = spinnerAdapter
//
//                        // Spinner 아이템 선택 리스너 설정
//                        binding.productDropdownCategory.onItemSelectedListener = object :
//                            AdapterView.OnItemSelectedListener {
//                            override fun onItemSelected(
//                                parent: AdapterView<*>,
//                                view: View?,
//                                position: Int,
//                                id: Long
//                            ) {
//                                if (position == 0) {
//                                    // Hint 선택 시
//
//                                } else {
//                                    // 실제 카테고리 선택 시
//                                    Toast.makeText(
//                                        requireContext(),
//                                        "선택된 카테고리: ${categoryNames[position]}",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//
//                            override fun onNothingSelected(parent: AdapterView<*>) {
//                                // 아무 것도 선택하지 않았을 때 처리할 로직
//                            }
//                        }
//                    } else {
//                        Toast.makeText(requireContext(), "카테고리 데이터 없음", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "카테고리 로드 실패: ${response.code()}", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "에러 발생: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


}