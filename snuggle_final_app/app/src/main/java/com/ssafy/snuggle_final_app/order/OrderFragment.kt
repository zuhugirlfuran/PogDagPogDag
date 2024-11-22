package com.ssafy.snuggle_final_app.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.findViewById<View>(R.id.bottomNavigation)?.visibility = View.GONE
        }

        binding.orderBtn.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frameLayout, OrderCompleteFragment()) // `R.id.main_frameLayout`은 프래그먼트 교체할 컨테이너 ID
            transaction.addToBackStack(null) // 이전 화면으로 돌아갈 수 있게 스택에 추가
            transaction.commit()
        }
    }

}