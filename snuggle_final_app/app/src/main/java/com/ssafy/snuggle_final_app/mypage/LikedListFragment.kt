package com.ssafy.snuggle_final_app.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.data.model.dto.Like
import com.ssafy.snuggle_final_app.databinding.FragmentLikedListBinding


class LikedListFragment : Fragment() {
    private var _binding: FragmentLikedListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikedListBinding.inflate(inflater, container, false)

        val dataList = listOf(
            Like("id 01", 1, System.currentTimeMillis().toString()),
            Like("id 01", 1, System.currentTimeMillis().toString()),
            Like("id 01", 1, System.currentTimeMillis().toString())
        )

        val adapter = LikedListAdapter(requireContext(), dataList)
        binding.likeLv.adapter = adapter

        return binding.root
    }


}