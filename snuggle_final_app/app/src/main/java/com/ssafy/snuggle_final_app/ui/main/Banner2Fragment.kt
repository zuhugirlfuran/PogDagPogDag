package com.ssafy.snuggle_final_app.ui.main

import android.os.Bundle
import android.view.View
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentBanner2Binding

class Banner2Fragment : BaseFragment<FragmentBanner2Binding>(
    FragmentBanner2Binding::bind,
    R.layout.fragment_banner2
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            // 배너 클릭 시 동작
            (activity as? MainActivity)?.addToStackFragment(BannerDetailFragment2())
        }
    }
}