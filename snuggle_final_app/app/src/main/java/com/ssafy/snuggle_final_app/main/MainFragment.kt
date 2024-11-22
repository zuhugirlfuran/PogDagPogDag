package com.ssafy.snuggle_final_app.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentMainBinding
import com.ssafy.snuggle_final_app.product.ProductDetailFragment
import com.ssafy.snuggle_final_app.product.ProductDetailFragmentViewModel

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
    private val productViewModel : ProductDetailFragmentViewModel by activityViewModels()

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

    }

    private fun observeViewModel() {
        productViewModel.bestProductList.observe(viewLifecycleOwner) {bestProducts ->
            if (bestProducts != null) {
               bestAdapter.submitList(bestProducts)
            }
        }

        productViewModel.newProductList.observe(viewLifecycleOwner) {newProducts ->
            if (newProducts != null) {
                newAdapter.submitList(newProducts)
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
            mainActivity.replaceFragment(ProductDetailFragment())

        }

        bestRecyclerView.adapter = bestAdapter
    }

    private fun newProductAdapter() {

        // RecyclerView 설정
        val newRecyclerView = binding.newProductRecyclerView
        newRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 어댑터 초기화 및 연결
        newAdapter = NewProductRecyclerViewAdapter(emptyList()) {productId ->
            productViewModel.productId = productId
            mainActivity.replaceFragment(ProductDetailFragment())
        }
        newRecyclerView.adapter = newAdapter
    }

}
