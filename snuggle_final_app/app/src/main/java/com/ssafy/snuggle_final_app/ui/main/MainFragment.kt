package com.ssafy.snuggle_final_app.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentMainBinding
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragment
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragmentViewModel
import com.ssafy.snuggle_final_app.ui.search.SearchFragment

private const val TAG = "MainFragment"
class MainFragment : BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::bind,
    R.layout.fragment_main
) {

    // 리사이클러 어댑터
    private lateinit var bestAdapter: BestProductRecyclerViewAdapter
    private lateinit var newAdapter: NewProductRecyclerViewAdapter

    // MainActivity
    private lateinit var mainActivity: MainActivity

    // viewModel
    private val productViewModel: ProductDetailFragmentViewModel by activityViewModels()
//    private val searchViewModel: SearchViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 툴바 숨기기
        val toolbar = activity?.findViewById<Toolbar>(R.id.app_bar)
        toolbar?.visibility = View.VISIBLE

        /* ==== banner ==== */
        //배너 클릭 리스너
        binding.bannerDetailBtn.setOnClickListener {
            val bannerTransaction = parentFragmentManager.beginTransaction()
            bannerTransaction.replace(R.id.main_frameLayout, BannerDetailFragment())
            bannerTransaction.addToBackStack(null)
            bannerTransaction.commit()
        }

        binding.bannerLayout.setOnClickListener {
            val bannerTransaction = parentFragmentManager.beginTransaction()
            bannerTransaction.replace(R.id.main_frameLayout, BannerDetailFragment())
            bannerTransaction.addToBackStack(null)
            bannerTransaction.commit()
        }

        // 어댑터 초기화
        bestProductAdapter()
        newProductAdapter()

        // viewModel 적용
        observeViewModel()

        /* ==== BestProduct ==== */
        productViewModel.getBestProductList()

        /* ==== NewProduct ==== */
        productViewModel.getNewProductList()

        // 검색 기능
        binding.searchEditText.setOnClickListener {
            mainActivity.addToStackFragment(SearchFragment())
        }

    }

    private fun observeViewModel() {
        productViewModel.bestProductList.observe(viewLifecycleOwner) { bestProducts ->
            if (bestProducts != null) {
                // 데이터 3개 제한
                val limitedBestProducts = if (bestProducts.size > 3) bestProducts.subList(0, 3) else bestProducts
                bestAdapter.submitList(limitedBestProducts)
            }
        }

        productViewModel.newProductList.observe(viewLifecycleOwner) { newProducts ->
            if (newProducts != null) {
                // 데이터 3개 제한
                val limitedNewProducts = if (newProducts.size > 3) newProducts.subList(0, 3) else newProducts
                newAdapter.submitList(limitedNewProducts)
            }
        }
    }


    private fun bestProductAdapter() {

        // RecyclerView 설정
        val bestRecyclerView = binding.bestProductRecyclerView
        bestRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        // 어댑터 초기화 및 연결
        bestAdapter = BestProductRecyclerViewAdapter(emptyList()) { productId ->
            productViewModel.productId = productId
            mainActivity.addToStackFragment(ProductDetailFragment())
        }

        bestRecyclerView.adapter = bestAdapter
    }

    private fun newProductAdapter() {

        // RecyclerView 설정
        val newRecyclerView = binding.newProductRecyclerView
        newRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 어댑터 초기화 및 연결
        newAdapter = NewProductRecyclerViewAdapter(emptyList()) { productId ->
            productViewModel.productId = productId
            mainActivity.addToStackFragment(ProductDetailFragment())
        }
        newRecyclerView.adapter = newAdapter
    }
}
