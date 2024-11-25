package com.ssafy.snuggle_final_app.ui.cart

import OrderViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.databinding.FragmentCartBinding
import com.ssafy.snuggle_final_app.ui.order.OrderFragment
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma


class CartFragment : BaseFragment<FragmentCartBinding>(
    FragmentCartBinding::bind,
    R.layout.fragment_cart
) {
    private lateinit var mainActivity: MainActivity
    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var adapter: CartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter 초기화
        adapter = CartAdapter(requireContext(), mutableListOf(),
            onDeleteClickListener = { position -> deleteItem(position) }, // 삭제 처리
            onQuantityChange = { updatedList -> updateTotal(updatedList) } // 수량 변경 처리
        )

        // 리스트뷰와 어댑터 연결
        binding.cartLv.adapter = adapter

        // ViewModel의 shoppingList를 관찰하여 업데이트
        orderViewModel.shoppingCart.observe(viewLifecycleOwner) { shoppingList ->
            logProductIds(shoppingList)

            adapter.updateData(shoppingList) // 어댑터 데이터 갱신
            updateTotal(shoppingList)
        }
        
        // 장바구니로 이동 버튼
        binding.cartBtnOrder.setOnClickListener {
            if (orderViewModel.shoppingCart.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "장바구니가 비어 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                mainActivity.replaceFragment(OrderFragment()) // OrderFragment로 이동
            }
        }
    }


    // 삭제 처리 함수
    private fun deleteItem(position: Int) {
        val currentList =
            orderViewModel.shoppingCart.value?.toMutableList() ?: return // 변경 가능 리스트로 복사

        if (position in currentList.indices) { // 유효한 인덱스인지 확인
            val removedCart = currentList[position] // 삭제된 아이템 확인
            currentList.removeAt(position) // 아이템 삭제

            orderViewModel.updateShoppingList(currentList) // ViewModel에 업데이트
            adapter.updateData(currentList) // 어댑터에 변경된 리스트 반영
            updateTotal(currentList) // 총 개수와 총 금액 업데이트

            // 삭제된 productId 로그 출력
            logRemovedProductId(removedCart)
        } else {
            android.util.Log.e(
                "CartFragment",
                "Invalid position: $position for list size ${currentList.size}"
            )
        }

    }

    private fun updateTotal(shoppingList: List<Cart>) {
        var totalCount = 0
        var totalPrice = 0

        for (cart in shoppingList) {
            totalCount += cart.productCnt
            totalPrice += cart.price * cart.productCnt  // 개수 * 가격
        }

        binding.cartTvTotalCount.text = "총 ${totalCount}개"
        binding.cartTvTotalPrice.text = makeComma(totalPrice)
    }


    override fun onResume() {
        super.onResume()
        // ConstraintLayout에서도 동일하게 BottomNavigationView 숨기기
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.GONE
    }


    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility =
            View.VISIBLE
    }

    // productId 로그 출력
    private fun logProductIds(shoppingList: List<Cart>) {
        shoppingList.forEach { cart ->
            android.util.Log.d("CartFragment", "Product ID in Cart: ${cart.productId}")
        }
    }

    // 삭제된 productId 로그 출력
    private fun logRemovedProductId(cart: Cart) {
        android.util.Log.d("CartFragment", "Removed Product ID: ${cart.productId}")

    }
}