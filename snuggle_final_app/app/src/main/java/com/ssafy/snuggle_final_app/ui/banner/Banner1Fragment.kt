package com.ssafy.snuggle_final_app.ui.banner

import android.os.Bundle
import android.view.View
import com.ssafy.snuggle_final_app.MainActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.databinding.FragmentBanner1Binding
import com.ssafy.snuggle_final_app.ui.main.BannerDetailFragment


class Banner1Fragment : BaseFragment<FragmentBanner1Binding>(
    FragmentBanner1Binding::bind,
    R.layout.fragment_banner1
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            // 배너 클릭 시 동작
            (activity as? MainActivity)?.addToStackFragment(BannerDetailFragment())
        }
    }

}