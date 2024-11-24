package com.ssafy.snuggle_final_app.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentLikedListBinding
import com.ssafy.snuggle_final_app.ui.mypage.LikeListViewModel
import com.ssafy.snuggle_final_app.ui.mypage.LikedListAdapter
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragment
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragmentViewModel


class LikedListFragment : BaseFragment<FragmentLikedListBinding>(
    FragmentLikedListBinding::bind,
    R.layout.fragment_liked_list
) {

    private val likeListViewModel: LikeListViewModel by viewModels()
    private val productViewModel: ProductDetailFragmentViewModel by activityViewModels()
    private lateinit var adapter: LikedListAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()
        // Activity의 BottomNavigationView를 숨김
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
        likeListViewModel.getLikeProductList(userId)

        likeListViewModel.likeProductList.observe(viewLifecycleOwner) { likeList ->
            adapter = LikedListAdapter(requireContext(), likeList) { product ->
                val productId = product.productId
                Log.d("AdapterClick", "Clicked productId: $productId")
                if (productId >= 0) { // 유효한 productId만 설정
                    productViewModel.productId = productId
                    mainActivity.addToStackFragment(ProductDetailFragment())
                }
            }
            binding.likeLv.adapter = adapter
        }

    }


    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility =
            View.VISIBLE
    }
}