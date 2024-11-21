package com.ssafy.snuggle_final_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentMainBinding
import com.ssafy.snuggle_mobile.main.BestProduct
import com.ssafy.snuggle_mobile.main.BestProductRecyclerViewAdapter
import com.ssafy.snuggle_mobile.main.NewProduct

class MainFragment : Fragment() {

    // 리사이클러 어댑터
    private lateinit var bestAdapter: BestProductRecyclerViewAdapter
    private lateinit var newAdapter: NewProductRecyclerViewAdapter

    // MainActivity
    private lateinit var mainActivity: MainActivity

    // 아이템 목록 저장
    private lateinit var bestProductList: MutableList<BestProduct>
    private lateinit var newProductList: MutableList<NewProduct>

    // 바인딩 객체 선언 및 초기화
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MainActivity의 인스턴스를 가져옴
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        /* ==== BestProduct ==== */
        // 상품 아이템 목록 초기화
        bestProductList = mutableListOf(
            BestProduct(R.drawable.item01, "푸딩거북이", "5,000원"),
            BestProduct(R.drawable.item02, "치즈버거", "8,000원"),
            BestProduct(R.drawable.item03, "아이스크림", "3,500원")
        )

        // RecyclerView 설정
        val bestRecyclerView = binding.bestProductRecyclerView
        bestRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 어댑터 초기화 및 연결
        bestAdapter = BestProductRecyclerViewAdapter(bestProductList)
        bestRecyclerView.adapter = bestAdapter

        /* ==== NewProduct ==== */
        // 상품 아이템 목록 초기화
        newProductList = mutableListOf(
            NewProduct(R.drawable.item02, "푸딩거북이", "5,000원"),
            NewProduct(R.drawable.item03, "치즈버거", "8,000원"),
            NewProduct(R.drawable.item04, "아이스크림", "3,500원")
        )

        // RecyclerView 설정
        val newRecyclerView = binding.newProductRecyclerView
        newRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 어댑터 초기화 및 연결
        newAdapter = NewProductRecyclerViewAdapter(newProductList)
        newRecyclerView.adapter = newAdapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
