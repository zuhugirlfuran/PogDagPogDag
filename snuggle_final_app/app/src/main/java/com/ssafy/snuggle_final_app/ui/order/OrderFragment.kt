package com.ssafy.snuggle_final_app.ui.order

import OrderViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Address
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.dto.OrderDetail
import com.ssafy.snuggle_final_app.databinding.FragmentOrderBinding
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
    private val addressViewModel: AddressViewModel by activityViewModels()
    private val applicationId = "6741ef35cc5274a3ac3fc73f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 어댑터 초기화
        initAdapter()
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId


        // viewModel 관찰
        observeOrderViewModel()
        checkAddress(userId)

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

    private fun checkAddress(userId: String) {
        // 주소 존재 여부 확인
        addressViewModel.isExist(userId)

        // 주소 존재 여부 관찰
        addressViewModel.isAddressExists.observe(viewLifecycleOwner) { exists ->
            if (exists) {  // 주소 데이터 이미 있을때
                addressViewModel.getAddress(userId)
            } else {
                enableAddressEditMode()
            }
        }

        // 주소 데이터 관찰
        addressViewModel.address.observe(viewLifecycleOwner) { address ->

            if (address != null) {
                Log.d(TAG, "checkAddress: 주소 데이터 업데이트됨 - $address")
                updateAddressUI(address)
            }
        }
    }

    private fun updateAddressUI(address: Address) {
        binding.orderTvAddr.text = address.address
        binding.orderTvAddr.visibility = View.VISIBLE
        binding.orderEtAddr.visibility = View.GONE

        binding.orderTvName.text = address.userName
        binding.orderTvName.visibility = View.VISIBLE
        binding.orderEtName.visibility = View.GONE

        binding.orderTvPhone.text = address.phone
        binding.orderTvPhone.visibility = View.VISIBLE
        binding.orderEtPhone.visibility = View.GONE
    }

    private fun enableAddressEditMode() {
        binding.orderTvAddr.visibility = View.GONE
        binding.orderEtAddr.visibility = View.VISIBLE

        binding.orderTvName.visibility = View.GONE
        binding.orderEtName.visibility = View.VISIBLE

        binding.orderTvPhone.visibility = View.GONE
        binding.orderEtPhone.visibility = View.VISIBLE
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
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
        // 주문 성공 후 주소 데이터 다시 가져오기
        addressViewModel.getAddress(userId)

        // 주문 완료 화면으로 이동
        val orderCompleteFragment = OrderCompleteFragment().apply {
            arguments = Bundle().apply {
                putString(
                    "address",
                    if (binding.orderTvAddr.visibility == View.VISIBLE)
                        binding.orderTvAddr.text.toString()
                    else
                        binding.orderEtAddr.text.toString()
                )
                putString(
                    "name",
                    if (binding.orderTvName.visibility == View.VISIBLE)
                        binding.orderTvName.text.toString()
                    else
                        binding.orderEtName.text.toString()
                )
                putString(
                    "phone",
                    if (binding.orderTvPhone.visibility == View.VISIBLE)
                        binding.orderTvPhone.text.toString()
                    else
                        binding.orderEtPhone.text.toString()
                )
            }
        }

        // 결제 완료 영수증으로 넘기기
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
        // 주소 생성
        makeAddress()

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

    private fun makeAddress() {
        // 이미 주소가 있는 경우 새 주소를 생성하지 않음
        if (binding.orderTvAddr.visibility == View.VISIBLE) {
            Log.d(TAG, "makeAddress: 이미 주소가 존재함, 새 주소 생성 안 함")
            return
        }

        val address = binding.orderEtAddr.text.toString()
        val name = binding.orderEtName.text.toString()
        val phone = binding.orderEtPhone.text.toString()

        // 주소 데이터 검증
        if (address.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(context, "주소, 이름, 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId

        // Address 객체 생성
        val newAddress = Address(
            userId = userId,
            userName = name,
            address = address,
            phone = phone
        )

        // 주소 삽입
        addressViewModel.insertAddress(newAddress)
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


    override fun onResume() {
        super.onResume()
        // Activity의 BottomNavigationView를 숨김
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility =
            View.VISIBLE
    }
}

