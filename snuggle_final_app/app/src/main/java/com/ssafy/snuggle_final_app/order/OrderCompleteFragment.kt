package com.ssafy.snuggle_final_app.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.databinding.FragmentOrderCompleteBinding
import com.ssafy.snuggle_final_app.main.MainActivityViewModel
import com.ssafy.snuggle_final_app.main.MainFragment
import com.ssafy.snuggle_final_app.mypage.MypageFragment

class OrderCompleteFragment : Fragment() {

    private var _binding: FragmentOrderCompleteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderCompleteBinding.inflate(inflater, container, false)

        // ViewModel 초기화
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        setupObserver()

        // Fragment가 전달받은 인자를 바인딩에 반영
        binding.orderCompleteAddr.text = arguments?.getString("address") ?: "주소 정보 없음"
        binding.orderCompleteUserName.text = arguments?.getString("name") ?: "이름 없음"
        binding.orderCompletePhone.text = arguments?.getString("phone") ?: "연락처 없음"

        return binding.root
    }

    private fun setupObserver() {
        // ViewModel 데이터 관찰 및 UI 업데이트
        viewModel.shoppingCart.observe(viewLifecycleOwner) { shoppingList ->
            val totalItems = shoppingList.sumOf { it.productCnt }
            val totalPrice = shoppingList.sumOf { it.price * it.productCnt }

            binding.orderCompleteTotalCount.text = "$totalItems 개"
            binding.orderCompleteTotalPrice.text = "$totalPrice 원"

            // 리스트뷰 갱신 (OrderAdapter에서 notifyDataSetChanged 활용)
            val adapter = OrderAdapter(requireContext(), shoppingList.toMutableList())
            binding.orderCompleteLv.adapter = adapter
        }
    }

    private fun navigateToMainFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, MainFragment())
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderCompleteBtn.setOnClickListener {
            // 메인 화면으로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, MypageFragment())
                .commit()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 바인딩 메모리 해제
    }
}
