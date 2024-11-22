package com.ssafy.snuggle_final_app.product

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
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
import com.ssafy.snuggle_final_app.search.SearchFragment
import kotlinx.coroutines.launch

class ProductFragment : BaseFragment<FragmentProductBinding>(
    FragmentProductBinding::bind,
    R.layout.fragment_product
) {

    private lateinit var adapter: ProductAdapter
    private lateinit var mainActivity: MainActivity

    private val productViewModel: ProductDetailFragmentViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    //    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        // livedata 통해서 관찰하고 리사이클러뷰 업데이트
        observeViewModel()
        observeCategoryViewModel()
        observeSortedViewModel()

        // 상품 리스트 데이터 가져오기
        productViewModel.getProductList()
        // 카테고리 데이터 가져오기
        categoryViewModel.getCategoryList()

        // 검색 기능
        binding.productEtSearch.setOnClickListener {
            mainActivity.addToStackFragment(SearchFragment())
        }

        sortSpinner()
    }

    private fun observeCategoryViewModel() {
        categoryViewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            if (categories.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "카테고리 목록이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                updateCategorySpinner(categories.map { it.categoryName })
            }
        }
    }

    private fun observeViewModel() {

        // 전체 상품 불러오기
        productViewModel.productList.observe(viewLifecycleOwner) { products ->
            if (products.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "상품 목록이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("TAG", "observeViewModel: ${products}")
                adapter.submitList(products)
            }
        }

        // 상품 id에 따른 info 불러오기
//        productViewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->
//           productInfo?.let {
//                 mainActivity.addToStackFragment(ProductDetailFragment())
//            }
//        }
    }

    private fun observeSortedViewModel() {
        productViewModel.sortedProductList.observe(viewLifecycleOwner) { sortedProducts ->
            if (sortedProducts != null) {
                adapter.submitList(sortedProducts) // RecyclerView 어댑터 갱신
            }
        }
    }

    private fun initAdapter() {

        adapter = ProductAdapter(emptyList()) { productId ->
            Log.d("AdapterClick", "Clicked productId: $productId")
            if (productId >= 0) { // 유효한 productId만 설정
                productViewModel.productId = productId
                mainActivity.addToStackFragment(ProductDetailFragment())
            }
        }

        binding.productRecyclerview.adapter = adapter
        binding.productRecyclerview.layoutManager = GridLayoutManager(requireContext(), 3)

    }

    private fun updateCategorySpinner(categoryNames: List<String>) {

        val categoryWithHint = mutableListOf("카테고리").apply {
            addAll(categoryNames)
        }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categoryWithHint
        )

        binding.productDropdownCategory.adapter = spinnerAdapter

        // 기본값으로 첫 번째 항목 선택(힌트)
        binding.productDropdownCategory.setSelection(0)

        binding.productDropdownCategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    // 카테고리 선택 안했을 때
                } else {
                    // 카테고리 선택 시 처리 로직
                    Toast.makeText(
                        requireContext(),
                        "선택된 카테고리: ${categoryNames[position]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무 것도 선택하지 않았을 때 처리할 로직
            }
        }
    }

    private fun sortSpinner() {
        // 고정된 항목 리스트 생성
        val fixedItems = listOf("정렬기준", "인기순", "신규순", "높은가격순", "낮은가격순")

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            fixedItems
        )

        // 스피너에 어댑터 연결
        binding.productDropdownSort.adapter = spinnerAdapter

        // 기본값 선택 (첫 번째 항목)
        binding.productDropdownSort.setSelection(0)

        // 스피너 아이템 선택 리스너 설정
        binding.productDropdownSort.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    // 선택된 항목 처리 로직
                    Toast.makeText(
                        requireContext(),
                        "선택된 항목: ${fixedItems[position]}",
                        Toast.LENGTH_SHORT
                    ).show()
                    applySortOption(fixedItems[position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무 것도 선택하지 않았을 때 처리할 로직 (필요 시 추가)
            }
        }
    }

    fun applySortOption(option: String) {
        val currentProducts = productViewModel.productList.value ?: return

        val sortedProducts = when (option) {
            "인기순" -> currentProducts.sortedByDescending { it.likeCount } // `popularity`는 가정된 속성
            "신규순" -> currentProducts.sortedByDescending { it.productId }  // `dateAdded`는 가정된 속성
            "높은가격순" -> currentProducts.sortedByDescending { it.price }  // 높은 가격순
            "낮은가격순" -> currentProducts.sortedBy { it.price }            // 낮은 가격순
            else -> currentProducts
        }

        // 정렬된 데이터 업데이트
        productViewModel.sortedProductList.value = sortedProducts
    }


}