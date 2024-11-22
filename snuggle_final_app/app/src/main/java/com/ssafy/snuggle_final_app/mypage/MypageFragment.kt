package com.ssafy.snuggle_final_app.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.network.RetrofitClient
import com.ssafy.snuggle_final_app.data.service.UserService
import com.ssafy.snuggle_final_app.databinding.FragmentMypageBinding

private const val TAG = "MypageFragment_싸피"

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar =
            requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        val menuCart = requireActivity().findViewById<View>(R.id.app_bar_ib_cart) // Cart 버튼
        val menuNotification =
            requireActivity().findViewById<View>(R.id.app_bar_ib_notification) // Notification 버튼

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        menuCart.setBackgroundColor(Color.parseColor("#FFE57D"))
        menuNotification.setBackgroundColor(Color.parseColor("#FFE57D"))

        //=== User 정보 가져오기 ==//
        val userService = RetrofitClient.instance.create(UserService::class.java)

//        userService.getUserInfo("id 01").enqueue(object : retrofit2.Callback<UserResponse> {
//            override fun onResponse(
//                call: retrofit2.Call<UserResponse>,
//                response: retrofit2.Response<UserResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val user = response.body()?.user
//                    Log.d(TAG, "Nickname: ${user?.nickname}, Stamps: ${user?.stamps}")
//                    view.findViewById<TextView>(R.id.mypage_tv_name).text = user?.nickname
//                    view.findViewById<TextView>(R.id.mypage_tv_stamp).text = user?.stamps.toString()
//                } else {
//                    Log.e(TAG, "Response Code: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
//                Log.e(TAG, "Failed to fetch user info", t)
//            }
//        })

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
