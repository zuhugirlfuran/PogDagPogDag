package com.ssafy.snuggle_final_app.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.dto.OrderDetail
import com.ssafy.snuggle_final_app.databinding.FragmentOrderListBinding

class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)

        // Adapter 연결
        val dataList = listOf(
            Pair(
                Order(orderId = 1, userId = "id01", addressId = 101, totalPrice = 5000.0),
                listOf(
                    OrderDetail(
                        detailId = 1, orderId = 1, productId = 1, quantity = 1,
                        orderTime = "2024-12-25T12:23:15", completed = "N"
                    )
                )
            ),
            Pair(
                Order(orderId = 2, userId = "id01", addressId = 101, totalPrice = 15000.0),
                listOf(
                    OrderDetail(
                        detailId = 2, orderId = 2, productId = 2, quantity = 2,
                        orderTime = "2024-12-25T14:45:10", completed = "Y"
                    )
                )
            )
        )

        val adapter = OrderListAdapter(requireContext(), dataList)
        binding.orderlistLv.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
