package com.ssafy.snuggle_final_app.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.snuggle_final_app.R

class MypageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app_bar.xml에서 툴바 참조
        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        val menuCart = requireActivity().findViewById<View>(R.id.app_bar_ib_cart) // Cart 버튼
        val menuNotification = requireActivity().findViewById<View>(R.id.app_bar_ib_notification) // Notification 버튼

        // 툴바를 ActionBar로 설정
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 초기 색상 설정 (onViewCreated에서 변경 가능)
        menuCart.setBackgroundColor(Color.parseColor("#FFE57D"))
        menuNotification.setBackgroundColor(Color.parseColor("#FFE57D"))
    }

    override fun onResume() {
        super.onResume()

        // 상태바 색상 변경
        requireActivity().window.statusBarColor = Color.parseColor("#FFE57D")

        // 툴바 배경색 변경
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFE57D")))

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
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFAE6")))

        // 메뉴 버튼 배경색 복원
        val menuCart = requireActivity().findViewById<View>(R.id.app_bar_ib_cart)
        val menuNotification = requireActivity().findViewById<View>(R.id.app_bar_ib_notification)
        menuCart.setBackgroundColor(Color.parseColor("#FFFAE6"))
        menuNotification.setBackgroundColor(Color.parseColor("#FFFAE6"))
    }
}
