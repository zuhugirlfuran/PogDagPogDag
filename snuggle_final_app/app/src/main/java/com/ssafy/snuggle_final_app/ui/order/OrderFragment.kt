package com.ssafy.snuggle_final_app.ui.order

import com.ssafy.snuggle_final_app.ui.cart.OrderAdapter

import OrderViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.dto.OrderDetail
import com.ssafy.snuggle_final_app.databinding.FragmentOrderBinding
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma
import kr.co.bootpay.Bootpay
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "OrderFragment"

class OrderFragment : BaseFragment<FragmentOrderBinding>(
    FragmentOrderBinding::bind,
    R.layout.fragment_order
) {

    private lateinit var mainActivity: MainActivity
    private lateinit var adapter: OrderAdapter

    private val orderViewModel: OrderViewModel by activityViewModels()
    private val applicationId = "6741ef35cc5274a3ac3fc73f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity

        // OrderViewModel 초기화
//        orderViewModel = ViewModelProvider(
//            this,
//            OrderViewModelFactory(RetrofitUtil.orderService)
//        )[OrderViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 어댑터 초기화
        initAdapter()

        // viewModel 관찰
        observeOrderViewModel()


        binding.orderBtn.setOnClickListener {
            val shoppingList = orderViewModel.shoppingCart.value
            if (shoppingList.isNullOrEmpty()) {
                Toast.makeText(context, "장바구니가 비어 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            makeOrder()

            // 부트페이 결제창 열기            
            openPayment(shoppingList)
        }

    }

    private fun initAdapter() {
        // Adapter 초기화
        adapter = OrderAdapter(requireContext(), mutableListOf())
        // 리스트뷰와 어댑터 연결
        binding.orderLv.adapter = adapter
    }


    private fun observeOrderViewModel() {
        // ViewModel의 shoppingCart를 관찰하여 UI 업데이트
        orderViewModel.shoppingCart.observe(viewLifecycleOwner) { shoppingList ->
            adapter.updateData(shoppingList) // 어댑터 데이터 갱신
            updateTotal(shoppingList)  // 총 개수와 총 금액 업데이트
        }

        // 주문 생성 결과를 관찰
        orderViewModel.isOrderMaked.observe(viewLifecycleOwner) { isOrdered ->
            if (isOrdered != null && isOrdered > 0) {
                handleOrderSuccess()  // 주문 성공 시, 주문 완료 화면으로 데이터 보내기

            } else if (isOrdered == 0) {
                Toast.makeText(context, "주문에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleOrderSuccess() {

        Log.d("OrderFragment", "Order Success")

        // 주문 완료 화면으로 이동
        val orderCompleteFragment = OrderCompleteFragment().apply {
            arguments = Bundle().apply {
                putString("address", binding.orderEtAddr.text.toString())
                putString("name", binding.orderEtName.text.toString())
                putString("phone", binding.orderEtPhone.text.toString())
            }
        }
        mainActivity.replaceFragment(orderCompleteFragment)
    }

    private fun updateTotal(shoppingList: List<Cart>) {
        var totalCount = 0
        var totalPrice = 0

        for (cart in shoppingList) {
            totalCount += cart.productCnt
            totalPrice += cart.price * cart.productCnt  // 개수 * 가격
        }

        binding.orderTvTotalCount.text = totalCount.toString()
        binding.orderTvTotalPrice.text = makeComma(totalPrice)
    }

    private fun makeOrder() {

        // ViewModel에서 현재 장바구니 정보를 가져와 Order 객체 생성
        val shoppingList = orderViewModel.shoppingCart.value
        if (shoppingList.isNullOrEmpty()) {
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
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
        val order = Order(
            orderId = 0, // 서버에서 생성
            userId = userId, // 고정된 사용자 ID
            addressId = 1, // 고정된 주소 ID
            totalPrice = totalPrice.toDouble(),
            details = orderDetails
        )

        // 요청 데이터 로그 출력
        Log.d("OrderFragment", "Sending Order Data: ${Gson().toJson(order)}")
        orderViewModel.makeOrder(order)

    }

    // 부트페이 결제
    private fun openPayment(shoppingList: List<Cart>) {
        val totalPrice = shoppingList.sumOf { it.productCnt * it.price }.toDouble()
        val totalCount = shoppingList.sumOf { it.productCnt }.toInt()
        val itemNames = shoppingList.map { it.title }
        val itemName = if (totalCount > 1) {
            "${itemNames[0]} 외 ${totalCount.toInt() - 1}개"
        } else {
            itemNames[0]
        }

        val bootUser = BootUser().setPhone("010-1234-5678")
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))

        Bootpay.init(requireActivity())
            .setApplicationId(applicationId) // 부트페이 Application ID
            .setPG(PG.KCP) // PG사 선택
            // .setMethod(Method.KAKAO) // 결제 수단 하나만 설정 가능. 지우면 선택지 중 선택 가능
            .setContext(requireContext()) // requireContext()로 null 방지
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG) // 결제창 유형
            .setName(itemName) // 결제할 상품명
            .setOrderId("1234") // 주문 고유번호. 없으면 에러남
            .setPrice(totalPrice) // 결제할 금액
            .onConfirm { message ->
                Log.d("Bootpay Confirm", message)
                Bootpay.confirm(message)
            }
            .onDone { message ->
                Log.d("Bootpay Done", "결제 완료: $message")
//                completeOrderDetails() // 결제완료시 OrderDetail의 complete 상태 변경
            }
            .onCancel { message ->
                Log.d("Bootpay Cancel", "결제 취소: $message")
            }
            .onError { message ->
                Log.d("Bootpay Error", "결제 에러: $message")
            }
            .onClose {
                Log.d("Bootpay Close", "결제창이 닫혔습니다.")
            }
            .request()
    }

//    private fun completeOrderDetails() {
//        val shoppingList = orderViewModel.shoppingCart.value
//        shoppingList?.forEach { cart ->
//            orderViewModel.updateOrderDetail(
//                OrderDetail(
//                    orderId = cart.orderId,
//                    productId = cart.productId,
//                    quantity = cart.productCnt,
//                    orderTime = SimpleDateFormat(
//                        "yyyy-MM-dd HH:mm:ss",
//                        Locale.getDefault()
//                    ).format(Date()),
//                    completed = "Y"
//                )
//            )
//        }
//    }


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

