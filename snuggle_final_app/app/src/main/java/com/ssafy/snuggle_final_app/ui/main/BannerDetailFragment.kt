package com.ssafy.snuggle_final_app.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentBannerDetailBinding


class BannerDetailFragment : BaseFragment<FragmentBannerDetailBinding>(
    FragmentBannerDetailBinding::bind,
    R.layout.fragment_banner_detail
) {

    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 툴바 숨기기
        val toolbar = activity?.findViewById<Toolbar>(R.id.app_bar)
        toolbar?.visibility = View.GONE
    }

}