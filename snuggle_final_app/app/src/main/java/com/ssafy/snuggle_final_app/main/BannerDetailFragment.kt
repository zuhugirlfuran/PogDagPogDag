package com.ssafy.snuggle_final_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.databinding.FragmentBannerDetailBinding
import com.ssafy.snuggle_final_app.databinding.FragmentMainBinding
import com.ssafy.snuggle_final_app.order.OrderFragment



class BannerDetailFragment : Fragment() {


    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentBannerDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBannerDetailBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 툴바 숨기기
        val toolbar = activity?.findViewById<Toolbar>(R.id.app_bar)
        toolbar?.visibility = View.GONE

    }

}