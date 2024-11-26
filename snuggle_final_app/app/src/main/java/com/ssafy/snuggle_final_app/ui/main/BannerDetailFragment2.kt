package com.ssafy.snuggle_final_app.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil.bind
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentBannerDetail2Binding

class BannerDetailFragment2 : BaseFragment<FragmentBannerDetail2Binding>(
    FragmentBannerDetail2Binding::bind,
    R.layout.fragment_banner_detail2
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