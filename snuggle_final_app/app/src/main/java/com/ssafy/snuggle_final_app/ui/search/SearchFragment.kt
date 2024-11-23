package com.ssafy.snuggle_final_app.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentSearchBinding
import com.ssafy.snuggle_final_app.ui.product.ProductAdapter
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragment
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragmentViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::bind,
    R.layout.fragment_search
) {

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val productViewModel: ProductDetailFragmentViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 상태 초기화
        searchViewModel.clearData()
        binding.searchEtSearch.setText("")

        // RecyclerView 설정
        adapter = ProductAdapter(emptyList()) {productId ->
            productViewModel.productId = productId
            mainActivity.addToStackFragment(ProductDetailFragment())
        }
        binding.searchRecyclerview.adapter = adapter
        binding.searchRecyclerview.layoutManager = GridLayoutManager(requireContext(), 3)

        // 검색어 관찰하여 EditText 업데이트
        searchViewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            if (query != null && binding.searchEtSearch.text.toString() != query) {
                binding.searchEtSearch.setText(query)
                binding.searchEtSearch.setSelection(query.length) // 커서를 끝으로 이동
            }
        }

        // 데이터 가져오기
        searchViewModel.fetchProducts()

        // 검색 결과 관찰
        searchViewModel.searchProducts.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products) // 검색 결과를 RecyclerView에 표시
        }

        // EditText의 텍스트 변경 감지
        binding.searchEtSearch.addTextChangedListener { text ->
            searchViewModel.updateSearchQuery(text.toString())
        }

        // 검색 버튼 클릭 이벤트 처리
        binding.searchIbSearch.setOnClickListener {
            val query = binding.searchEtSearch.text.toString()
            searchViewModel.updateSearchQuery(query) // 검색 실행
        }

    }


}