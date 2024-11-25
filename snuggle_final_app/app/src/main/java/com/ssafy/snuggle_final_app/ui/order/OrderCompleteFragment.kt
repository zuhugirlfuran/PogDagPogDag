package com.ssafy.snuggle_final_app.ui.order

import OrderViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentOrderCompleteBinding
import com.ssafy.snuggle_final_app.ui.cart.OrderAdapter
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma

class OrderCompleteFragment : BaseFragment<FragmentOrderCompleteBinding>(
    FragmentOrderCompleteBinding::bind,
    R.layout.fragment_order_complete
) {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var adapter: OrderAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()


        // Fragment가 전달받은 인자를 바인딩에 반영
        val userName = ApplicationClass.sharedPreferencesUtil.getUser().nickname
        binding.orderCompleteAddr.text = "사용자 주소"
        binding.orderCompleteUserName.text = userName
        binding.orderCompletePhone.text = "010-1111-1111"


        // 주문 완료 버튼
        binding.orderCompleteBtn.setOnClickListener {
            // 주문 완료 후 어댑터 리스트 초기화
            adapter.updateData(emptyList())

            // ViewModel의 쇼핑카트도 초기화
            orderViewModel.resetOrderState()
            //Toast.makeText(requireContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            // 메인 화면으로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, MypageFragment())
                .commit()
        }
    }

    private fun setupObserver() {
        // ViewModel 데이터 관찰 및 UI 업데이트
        orderViewModel.shoppingCart.observe(viewLifecycleOwner) { shoppingList ->
            val totalItems = shoppingList.sumOf { it.productCnt }
            val totalPrice = shoppingList.sumOf { it.price * it.productCnt }

            binding.orderCompleteTotalCount.text = "$totalItems 개"
            binding.orderCompleteTotalPrice.text = makeComma(totalPrice)

            // 리스트뷰 갱신 (OrderAdapter에서 notifyDataSetChanged 활용)
            adapter = OrderAdapter(requireContext(), shoppingList.toMutableList())
            binding.orderCompleteLv.adapter = adapter
        }
    }

    private fun navigateToMainFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, MainFragment())
            .commit()
    }
}
