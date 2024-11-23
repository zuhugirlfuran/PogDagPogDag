package com.ssafy.snuggle_final_app.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.dto.OrderDetail
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import com.ssafy.snuggle_final_app.databinding.FragmentOrderBinding
import com.ssafy.snuggle_final_app.main.MainActivityViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: OrderAdapter

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity

        // OrderViewModel 초기화
        orderViewModel = ViewModelProvider(
            this,
            OrderViewModelFactory(RetrofitUtil.orderService)
        )[OrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        // ViewModel 초기화
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        // Adapter 초기화
        adapter = OrderAdapter(requireContext(), mutableListOf()) // 초기 데이터는 빈 리스트

        // 리스트뷰와 어댑터 연결
        binding.orderLv.adapter = adapter

        // ViewModel의 shoppingList를 관찰하여 업데이트
        viewModel.shoppingCart.observe(viewLifecycleOwner) { shoppingList ->
            adapter.updateData(shoppingList) // 어댑터 데이터 갱신
            updateTotal(shoppingList)  // 총 개수와 총 금액 업데이트
        }

        return binding.root
    }

    private fun updateTotal(shoppingList: List<Cart>) {
        var totalCount = 0
        var totalPrice = 0

        for (cart in shoppingList) {
            totalCount += cart.productCnt
            totalPrice += cart.price * cart.productCnt  // 개수 * 가격
        }

        binding.orderTvTotalCount.text = totalCount.toString()
        binding.orderTvTotalPrice.text = "${totalPrice}원"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("OrderFragment", "onViewCreated called")
        viewModel.shoppingCart.value?.forEach { cart ->
            Log.d(
                "OrderFragment",
                "Product ID in Cart: ${cart.productId}, Quantity: ${cart.productCnt}"
            )
        }

        binding.orderBtn.setOnClickListener {

            makeOrder()

            val orderCompleteFragment = OrderCompleteFragment()

            // Bundle에 데이터 추가
            val bundle = Bundle()
//            bundle.putParcelableArrayList("shoppingList", ArrayList(viewModel.shoppingList.value ?: emptyList()))
            bundle.putString("address", binding.orderEtAddr.text.toString())
            bundle.putString("name", binding.orderEtName.text.toString())
            bundle.putString("phone", binding.orderEtPhone.text.toString())

            orderCompleteFragment.arguments = bundle

            // Fragment 교체
            mainActivity.replaceFragment(orderCompleteFragment)
        }

    }

    private fun makeOrder() {
        // ViewModel에서 현재 장바구니 정보를 가져와 Order 객체 생성
        viewModel.shoppingCart.value?.let { shoppingList ->
            if (shoppingList.isEmpty()) {
                Toast.makeText(context, "장바구니가 비어 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }

            // 총 가격 계산
            val totalPrice = shoppingList.sumOf { it.productCnt * it.price }

            // 주문 세부사항(OrderDetail) 생성
            val orderDetails = shoppingList.map { cart ->
                OrderDetail(
                    orderId = 0, // 서버에서 생성
                    productId = cart.productId,
                    quantity = cart.productCnt,
                    orderTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                        Date()
                    ),
                    completed = "N" // 초기 상태
                )
            }

            // 주문(Order) 객체 생성
            val order = Order(
                orderId = 0, // 서버에서 생성
                userId = "id 01", // 고정된 사용자 ID
                addressId = 1, // 고정된 주소 ID
                totalPrice = totalPrice.toDouble(),
                details = orderDetails
            )

            // 요청 데이터 로그 출력
            Log.d("OrderFragment", "Sending Order Data: ${Gson().toJson(order)}")

            // 서버에 주문 요청
//            lifecycleScope.launch {
//            try {
            orderViewModel.isOrderMaked.observe(viewLifecycleOwner) { isOrdered ->
                if (isOrdered > 0) {
                    val orderId = orderViewModel.makeOrder(order)
                    Toast.makeText(context, "주문이 완료되었습니다. Order ID: $orderId", Toast.LENGTH_SHORT)
                        .show()

                    // 로그 추가: 성공한 주문 데이터
                    Log.d("OrderFragment", "Order Success: ${Gson().toJson(order)}")

                    // 주문 완료 화면으로 이동
                    val orderCompleteFragment = OrderCompleteFragment().apply {
                        arguments = Bundle().apply {
                            putInt("orderId", orderId)
                            putString("address", binding.orderEtAddr.text.toString())
                            putString("name", binding.orderEtName.text.toString())
                            putString("phone", binding.orderEtPhone.text.toString())
                        }
                    }
                    mainActivity.replaceFragment(orderCompleteFragment)
                }
            }

        }

//            } catch (e: HttpException) {
//                Log.e("OrderFragment", "HTTP error during order: ${e.message}")
//                Toast.makeText(context, "주문 실패: 서버 오류 (${e.message})", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                Log.e("OrderFragment", "Unexpected error during order: ${e.message}")
//                Toast.makeText(context, "주문 실패: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//        }
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
