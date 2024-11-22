package com.ssafy.snuggle_final_app.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        // 리스트뷰 연결
        val dataList = listOf(

            Cart(
                R.drawable.item03,
                "푸딩거북이2",
                "11.30(토) 이내 발송예정",
                "5,000원"
            ),
            Cart(
                R.drawable.item03,
                "푸딩거북이2",
                "11.30(토) 이내 발송예정",
                "5,000원"
            ),
            Cart(
                R.drawable.item03,
                "푸딩거북이2",
                "11.30(토) 이내 발송예정",
                "5,000원"
            ),
        )

        val adapter = CartAdapter(requireContext(), dataList)
        binding.cartLv.adapter = adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Activity의 BottomNavigationView를 숨김
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility =
            View.VISIBLE
    }

}