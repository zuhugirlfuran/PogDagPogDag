package com.ssafy.snuggle_final_app.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.databinding.FragmentCartBinding
import com.ssafy.snuggle_final_app.main.MainActivityViewModel
import com.ssafy.snuggle_final_app.order.OrderFragment


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)


        // ViewModel 초기화
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        // Adapter 초기화
        // Adapter 초기화
        adapter = CartAdapter(requireContext(), mutableListOf()) { position ->
            deleteItem(position) // 삭제 처리
        }

        // 리스트뷰와 어댑터 연결
        binding.cartLv.adapter = adapter

//        val adapter = CartAdapter(requireContext(), dataList)
//        binding.cartLv.adapter = adapter

        // ViewModel의 shoppingList를 관찰하여 업데이트
        viewModel.shoppingCart.observe(viewLifecycleOwner) { shoppingList ->
            adapter.updateData(shoppingList) // 어댑터 데이터 갱신
            updateTotal(shoppingList)
        }

        return binding.root
    }

    // 삭제 처리 함수
    private fun deleteItem(position: Int) {
        val currentList = viewModel.shoppingCart.value?.toMutableList() ?: return // 변경 가능 리스트로 복사
        currentList.removeAt(position) // 아이템 삭제
        viewModel.updateShoppingList(currentList) // 업데이트
        updateTotal(currentList) // 총 개수와 총 금액 업데이트
    }

    private fun updateTotal(shoppingList: List<Cart>) {
        var totalCount = 0
        var totalPrice = 0

        for (cart in shoppingList) {
            totalCount += cart.productCnt
            totalPrice += cart.price * cart.productCnt  // 개수 * 가격
        }

        binding.cartTvTotalCount.text = "총 ${totalCount}개"
        binding.cartTvTotalPrice.text = "${totalPrice}원"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartBtnOrder.setOnClickListener {
            mainActivity.replaceFragment(OrderFragment())
        }
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

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}