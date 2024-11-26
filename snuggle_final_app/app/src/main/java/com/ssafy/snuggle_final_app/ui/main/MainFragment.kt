package com.ssafy.snuggle_final_app.ui.main

import BannerAdapter
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentMainBinding
import com.ssafy.snuggle_final_app.ui.banner.Banner1Fragment
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

    // ViewPager2 배너
    private lateinit var viewPager2: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private val pagerRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewPager2.currentItem
            val nextItem = (currentItem + 1) % (viewPager2.adapter?.itemCount ?: 1)
            viewPager2.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 2000)
        }
    }

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


        // 배너 설정
        setupBanner()

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


    private fun setupBanner() {
        val bannerAdapter = BannerAdapter(this)
        bannerAdapter.addBannerFragment(Banner1Fragment())  // 배너 1
        bannerAdapter.addBannerFragment(Banner2Fragment())  // 배너 2

        viewPager2 = binding.viewPagerBanner
        viewPager2.adapter = bannerAdapter


        // 배너 자동 스크롤 시작
        handler.postDelayed(pagerRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(pagerRunnable)  // 리소스 누수 방지
    }


    private fun observeViewModel() {
        productViewModel.bestProductList.observe(viewLifecycleOwner) { bestProducts ->
            if (bestProducts != null) {
                // 데이터 3개 제한
                val limitedBestProducts =
                    if (bestProducts.size > 5) bestProducts.subList(0, 5) else bestProducts
                bestAdapter.submitList(limitedBestProducts)
            }
        }

        productViewModel.newProductList.observe(viewLifecycleOwner) { newProducts ->
            if (newProducts != null) {
                // 데이터 3개 제한
                val limitedNewProducts =
                    if (newProducts.size > 5) newProducts.subList(0, 5) else newProducts
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
