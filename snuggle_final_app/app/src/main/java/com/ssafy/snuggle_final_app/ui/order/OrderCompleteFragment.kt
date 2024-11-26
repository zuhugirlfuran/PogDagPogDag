package com.ssafy.snuggle_final_app.ui.order

import BottomNavigationHelper
import OrderViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.databinding.FragmentOrderCompleteBinding
import com.ssafy.snuggle_final_app.ui.chatbot.ChatBotFragment
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragmentViewModel
import com.ssafy.snuggle_final_app.ui.product.ProductFragment
import com.ssafy.snuggle_final_app.ui.scanner.ScannerFragment
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma

private const val TAG = "OrderCompleteFragment"

class OrderCompleteFragment : BaseFragment<FragmentOrderCompleteBinding>(
    FragmentOrderCompleteBinding::bind,
    R.layout.fragment_order_complete
) {
    private val addressViewModel: AddressViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    private lateinit var adapter: OrderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()

        // 주문 완료 주소 정보 출력
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


        // 확인 버튼
        binding.orderCompleteBtn.setOnClickListener {
            // 주문 완료 후 어댑터 리스트 초기화
            adapter.updateData(emptyList())

            // ViewModel의 쇼핑카트도 초기화
            orderViewModel.resetOrderState()

            //Toast.makeText(requireContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            // 메인 화면으로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, MainFragment())
                .commit()

            // Navigation도 메인화면으로 돌리기
            val bottomNavigationHelper = BottomNavigationHelper(
                context = requireContext(),
                fragmentManager = parentFragmentManager,
                mainFrameId = R.id.main_frameLayout
            )

            bottomNavigationHelper.setup(
                backIndicator = requireActivity().findViewById(R.id.backIndicator2),
                tabs = listOf(
                    Triple(
                        requireActivity().findViewById(R.id.product),
                        requireActivity().findViewById(R.id.productIndicator2),
                        Triple(
                            requireActivity().findViewById(R.id.productIcon2),
                            requireActivity().findViewById(R.id.productLabel2),
                            ProductFragment()
                        )
                    ),
                    Triple(
                        requireActivity().findViewById(R.id.scanner),
                        requireActivity().findViewById(R.id.scannerIndicator2),
                        Triple(
                            requireActivity().findViewById(R.id.scannerIcon2),
                            requireActivity().findViewById(R.id.scannerLabel2),
                            ScannerFragment()
                        )
                    ),
                    Triple(
                        requireActivity().findViewById(R.id.home),
                        requireActivity().findViewById(R.id.homeIndicator2),
                        Triple(
                            requireActivity().findViewById(R.id.homeIcon2),
                            requireActivity().findViewById(R.id.homeLabel2),
                            MainFragment()
                        )
                    ),
                    Triple(
                        requireActivity().findViewById(R.id.chatbot),
                        requireActivity().findViewById(R.id.chatbotIndicator2),
                        Triple(
                            requireActivity().findViewById(R.id.chatbotIcon2),
                            requireActivity().findViewById(R.id.chatbotLabel2),
                            ChatBotFragment()
                        )
                    ),
                    Triple(
                        requireActivity().findViewById(R.id.mypage),
                        requireActivity().findViewById(R.id.mypageIndicator2),
                        Triple(
                            requireActivity().findViewById(R.id.mypageIcon2),
                            requireActivity().findViewById(R.id.mypageLabel2),
                            MypageFragment()
                        )
                    )
                ),
                defaultTab = requireActivity().findViewById(R.id.home) // 기본 탭으로 Home 설정
            )

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

    override fun onResume() {
        super.onResume()

        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.GONE
    }


    override fun onStop() {
        super.onStop()
        // Activity의 BottomNavigationView를 다시 보임
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility =
            View.VISIBLE
    }

    private fun navigateToMainFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, MainFragment())
            .commit()
    }
}
