package com.ssafy.snuggle_final_app.ui.mypage

import OrderListFragment
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.ssafy.snuggle_final_app.LoginActivity
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentMypageBinding
import com.ssafy.snuggle_final_app.mypage.BookmarkFragment
import com.ssafy.snuggle_final_app.mypage.LikedListFragment

private const val TAG = "MypageFragment_싸피"

class MypageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::bind,
    R.layout.fragment_mypage
) {

    private lateinit var mainActivity: MainActivity

    private val viewModel: MyPageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = context as MainActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //유저 정보 가져오기
        observeViewModel()

        val toolbar =
            requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        val menuCart = requireActivity().findViewById<View>(R.id.app_bar_ib_cart) // Cart 버튼
        val menuNotification =
            requireActivity().findViewById<View>(R.id.app_bar_ib_notification) // Notification 버튼

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        menuCart.setBackgroundColor(Color.parseColor("#FFE57D"))
        menuNotification.setBackgroundColor(Color.parseColor("#FFE57D"))


        //=== User 정보 가져오기 ==//
        //** 혹시 안불러와진다면 server 코드에서 userW -> W 삭제 && id -> userId로 이름 변경하세욥
        val userId = sharedPreferencesUtil.getUser().userId
        viewModel.getUserInfo(userId)
        Log.d(TAG, "onViewCreated: ${userId}")


        if (userId.isNotEmpty()) {
            // 쿠폰 데이터 가져오기
            viewModel.getCoupons(userId)
        }

        //=== 주문 내역으로 이동==//
        binding.mypageLlOrderlist.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, OrderListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        //==영상 책갈피로 이동==//
        binding.mypageLlBookmark.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, BookmarkFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        //== 관심상품으로 이동==//
        binding.mypageLlLike.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, LikedListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        //== 로그아웃 ==//
        binding.mypageBtnLogout.setOnClickListener {
            logout()
        }

        //== 쿠폰 ==//
        binding.mypageBtnCoupon.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, CouponFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    private fun logout() {
        //preference 지우기
        ApplicationClass.sharedPreferencesUtil.deleteUser()
        ApplicationClass.sharedPreferencesUtil.deleteUserCookie()

        //화면이동
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 현재 액티비티 스택을 모두 제거
        startActivity(intent)
        requireActivity().finish()
    }

    private fun observeViewModel() {
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            if (userInfo != null) {
                Log.d(TAG, "observeViewModel: ${userInfo.user}")
                binding.mypageTvName.text = userInfo.user.nickname

                var stampNum = userInfo.user.stamps
                binding.mypageTvStamp.text = stampNum.toString()

                //==등급 계산(임시)==//
                // 10개 모으면 rank up 하는걸로 설정 - 기본 레벨은 1
                var rank = stampNum / 10 + 1
                binding.mypageTvRank.text = "Lv." + rank.toString()
            }
        }

        // 쿠폰 데이터 관찰
        viewModel.coupons.observe(viewLifecycleOwner) { coupons ->
            if (coupons != null) {
                val availableCoupons = coupons.count { !it.couponUse }
                binding.mypageTvCoupon.text = availableCoupons.toString()
            } else {
                binding.mypageTvCoupon.text = "0"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // 상태바 색상 변경
        requireActivity().window.statusBarColor = Color.parseColor("#FFE57D")

        // 툴바 배경색 변경
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#FFE57D"))
        )

        // 툴바 아이콘 색상 변경
        val menuCart = requireActivity().findViewById<View>(R.id.app_bar_ib_cart)
        val menuNotification = requireActivity().findViewById<View>(R.id.app_bar_ib_notification)

        menuCart.setBackgroundColor(Color.parseColor("#FFE57D"))
        menuNotification.setBackgroundColor(Color.parseColor("#FFE57D"))
    }

    override fun onPause() {
        super.onPause()

        // 상태바 색상 복원
        requireActivity().window.statusBarColor = Color.parseColor("#FFFAE6")

        // 툴바 배경색 복원
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#FFFAE6"))
        )

        // 툴바 아이콘 색상 복원
        val menuCart = requireActivity().findViewById<View>(R.id.app_bar_ib_cart)
        val menuNotification = requireActivity().findViewById<View>(R.id.app_bar_ib_notification)

        menuCart.setBackgroundColor(Color.parseColor("#FFFAE6"))
        menuNotification.setBackgroundColor(Color.parseColor("#FFFAE6"))
    }

}
