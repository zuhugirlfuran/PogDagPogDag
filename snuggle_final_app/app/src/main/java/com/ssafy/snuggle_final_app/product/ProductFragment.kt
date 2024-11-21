package com.ssafy.snuggle_final_app.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.Product
import com.ssafy.snuggle_final_app.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {

        val productList = mutableListOf(
            Product(1, "푸딩거북이", "5000", R.drawable.item02),
            Product(2, "상어인형", "8000",R.drawable.item01),
            Product(3, "토끼인형", "7000",R.drawable.item03),
            Product(4, "곰인형", "10000",R.drawable.item04)
        )


        adapter = ProductAdapter(productList) { product ->
            Toast.makeText(requireContext(), "${product.name} 클릭됨", Toast.LENGTH_SHORT).show()
        }


        binding.productGridview.adapter = adapter
        binding.productGridview.layoutManager = GridLayoutManager(requireContext(), 3)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}