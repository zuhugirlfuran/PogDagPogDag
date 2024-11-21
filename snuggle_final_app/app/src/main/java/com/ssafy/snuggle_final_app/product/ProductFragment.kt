package com.ssafy.snuggle_final_app.product

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.snuggle_final_app.ApplicationClass
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.dto.Product
import com.ssafy.snuggle_final_app.databinding.FragmentProductBinding
import com.ssafy.snuggle_final_app.service.ProductService
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductAdapter
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        // 서버에 카테고리 연결해 출력하는 어댑터
        // categorySpinnerAdapter(1)
    }

    private fun initAdapter() {

        val productList = mutableListOf(
            Product(1, "푸딩거북이", "5000", R.drawable.item02),
            Product(2, "상어인형", "8000",R.drawable.item01),
            Product(3, "토끼인형", "7000",R.drawable.item03),
            Product(4, "곰인형", "10000",R.drawable.item04)
        )


        adapter = ProductAdapter(productList) {
            mainActivity.replaceFragment(ProductDetailFragment())
        }


        binding.productRecyclerview.adapter = adapter
        binding.productRecyclerview.layoutManager = GridLayoutManager(requireContext(), 3)

    }

    private fun categorySpinnerAdapter(cId: Int) {
        val categoryService = ApplicationClass.retrofit.create(ProductService::class.java)

        // CoroutineScope를 활용하여 데이터 가져오기
        lifecycleScope.launch {
            try {
                val response = categoryService.getCategory(cId)

                if (response.isSuccessful) {
                    val category = response.body()
                    if (category != null) {
                        val categoryNames = listOf(category.name)

                        // Spinner 어댑터 설정
                        val spinnerAdapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            categoryNames
                        )
                        binding.productDropdownCategory.adapter = spinnerAdapter

                        // Spinner 아이템 선택 리스너 설정
                        binding.productDropdownCategory.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position == 0) {
                                    // Hint 선택 시

                                } else {
                                    // 실제 카테고리 선택 시
                                    Toast.makeText(
                                        requireContext(),
                                        "선택된 카테고리: ${categoryNames[position]}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {
                                // 아무 것도 선택하지 않았을 때 처리할 로직
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "카테고리 데이터 없음", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "카테고리 로드 실패: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "에러 발생: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}