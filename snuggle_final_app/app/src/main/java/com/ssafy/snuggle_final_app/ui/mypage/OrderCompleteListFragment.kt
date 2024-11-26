package com.ssafy.snuggle_final_app.ui.mypage

import OrderCompleteAdapter
import OrderViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.OrderDetail
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.model.response.OrderDetailResponse
import com.ssafy.snuggle_final_app.data.model.response.OrderResponse
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import com.ssafy.snuggle_final_app.databinding.FragmentOrderCompleteListBinding
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.order.AddressViewModel
import com.ssafy.snuggle_final_app.ui.order.OrderAdapter
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragmentViewModel
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma
import kotlin.math.log


private const val TAG = "OrderCompleteListFragment"

class OrderCompleteListFragment : BaseFragment<FragmentOrderCompleteListBinding>(
    FragmentOrderCompleteListBinding::bind,
    R.layout.fragment_order_complete_list
) {
    private val addressViewModel: AddressViewModel by activityViewModels()
    private lateinit var adapter: OrderCompleteAdapter
    private val productViewModel: ProductDetailFragmentViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    var productIdList = listOf<OrderDetailResponse>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
        addressViewModel.getAddress(userId)
        addressViewModel.address.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                Log.d(TAG, "checkAddress: 주소 데이터 업데이트됨 - $data")
                val address = data.address
                val name = data.userName
                val phone = data.phone
                Log.d(TAG, "onViewCreated: ${address} ${name} ${phone}")
                binding.orderCompleteAddr.text = address
                binding.orderCompleteUserName.text = name
                binding.orderCompletePhone.text = phone
            }
        }

        setupObserver()


        // 확인 버튼
        binding.orderCompleteBtn.setOnClickListener {

            //Toast.makeText(requireContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            // 메인 화면으로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, MainFragment())
                .commit()
        }


    }

    private fun initAdapter() {
        adapter = OrderCompleteAdapter(
            context = requireContext(),
            orderList = mutableListOf(),
            productViewModel
        )

        binding.orderCompleteLv.adapter = adapter
    }

    //    private fun setupObserver() {
//
//        val orderId = arguments?.getInt("orderId") ?: 0
//        Log.d(TAG, "onViewCreated: orderId $orderId")
//        var totalCnt = 0
//
//        orderViewModel.getOrderDetail(orderId)
//        orderViewModel.orderDetail.observe(viewLifecycleOwner) { orderResponse ->
//            binding.orderCompleteDate.text = orderResponse.details[0].orderTime
//
//            // 어댑터에 데이터를 업데이트
//            adapter.updateData(orderResponse.details)
//
//            // 총 개수와 총 금액 계산 및 업데이트
//            totalCnt = orderResponse.details.sumOf { it.quantity }
//            val totalPrice = orderResponse.totalPrice.toInt()
//            productIdList = orderResponse.details
//
//            binding.orderCompleteTotalCount.text = totalCnt.toString()
//            binding.orderCompleteTotalPrice.text = makeComma(totalPrice)
//        }
//
//        binding.orderCompleteTotalCount.text = totalCnt.toString()
//    }
    private fun setupObserver() {
        val orderId = arguments?.getInt("orderId") ?: 0
        Log.d(TAG, "onViewCreated: orderId $orderId")

        orderViewModel.getOrderDetail(orderId)
        orderViewModel.orderDetail.observe(viewLifecycleOwner) { orderResponse ->
            // 주문 날짜 업데이트
            binding.orderCompleteDate.text = orderResponse.details[0].orderTime

            // 중복 제거된 OrderDetailResponse 리스트
            val uniqueDetails = orderResponse.details.distinctBy { it.productId }
            val productMap = mutableMapOf<Int, ProductWithCommentResponse>()

            // Product 데이터를 미리 가져와 Map에 저장
            uniqueDetails.forEach { detail ->
                productViewModel.getProductWithComments(detail.productId)
                productViewModel.productInfo.observe(viewLifecycleOwner) { productInfo ->
                  //  Log.d(TAG, "setupObserver: productInfo $productInfo")
                    productInfo?.let { productMap[detail.dId] = it }
                    Log.d(TAG, "setupObserver: productMap $productMap")
                    // Map 갱신 후 어댑터 데이터 업데이트
                    adapter.updateProductMap(productMap)
                }
            }
            
            // 어댑터에 OrderDetail 데이터 전달
            adapter.updateData(uniqueDetails)

            // 총 개수와 금액 계산
            val totalCnt = orderResponse.details.sumOf { it.quantity }
            val totalPrice = orderResponse.totalPrice.toInt()

            binding.orderCompleteTotalCount.text = totalCnt.toString()
            binding.orderCompleteTotalPrice.text = makeComma(totalPrice)
        }
    }

}