package com.ssafy.snuggle_final_app.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentCouponBinding

private const val TAG = "CouponFragment"
class CouponFragment : BaseFragment<FragmentCouponBinding>(
    FragmentCouponBinding::bind,
    R.layout.fragment_coupon
) {

    private val couponViewModel: CouponViewModel by activityViewModels()
    private lateinit var couponAdapter: CouponAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = context as MainActivity
        mainActivity.let {
            it.findViewById<View>(R.id.app_bar)?.visibility = View.GONE
            it.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.GONE
        }

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // RecyclerView 초기화
        setupRecyclerView()

        // ViewModel 관찰
        observeViewModel()

        // UserId 가져오기
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
        couponViewModel.getCoupons(userId)  // 쿠폰 리스트 보여주기

    }

    private fun setupRecyclerView() {
        binding.couponList.layoutManager = LinearLayoutManager(requireContext())
        couponAdapter = CouponAdapter(emptyList())
        binding.couponList.adapter = couponAdapter
    }

    private fun observeViewModel() {
        couponViewModel.couponList.observe(viewLifecycleOwner) { coupons ->
            if (coupons != null) {
                couponAdapter = CouponAdapter(coupons)
                binding.couponList.adapter = couponAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // 툴바랑 바텀 네비 보이기
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.findViewById<View>(R.id.app_bar)?.visibility = View.VISIBLE
            it.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.VISIBLE
        }
    }
}