package com.ssafy.snuggle_final_app.ui.navigation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ssafy.snuggle_final_app.R

class BottomNavigationHelper(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val mainFrameId: Int
) {

    fun setup(
        backIndicator: View,
        tabs: List<Triple<LinearLayout, View, Triple<ImageView, TextView, Fragment>>>,
        defaultTab: LinearLayout
    ) {
        // 초기 활성화할 탭 설정
        val defaultTabConfig = tabs.find { it.first == defaultTab }
        defaultTabConfig?.let { (tabLayout, indicator, content) ->
            val (icon, label, fragment) = content
            activateTab(indicator, icon, label, backIndicator, tabLayout)
            // ViewTreeObserver를 사용해 레이아웃이 준비된 후 위치 이동
            backIndicator.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    moveBackIndicator(backIndicator, tabLayout)
                    backIndicator.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
            replaceFragment(fragment)
        }

        // 클릭 리스너 설정
        tabs.forEach { tab ->
            val (tabLayout, indicator, content) = tab
            val (icon, label, fragment) = content

            tabLayout.setOnClickListener {
                activateTab(indicator, icon, label, backIndicator, tabLayout)
                deactivateAllExcept(indicator, icon, label, tabs)
                replaceFragment(fragment)
            }
        }
    }

    private fun activateTab(
        indicator: View,
        icon: ImageView,
        label: TextView,
        backIndicator: View,
        targetTab: View
    ) {
        indicator.visibility = View.VISIBLE
        backIndicator.visibility = View.VISIBLE

        // 아이콘 변경
        when (icon.id) {
            R.id.productIcon2 -> icon.setImageResource(R.drawable.product_active_icon)
            R.id.scannerIcon2 -> icon.setImageResource(R.drawable.scanner_active_icon)
            R.id.homeIcon2 -> icon.setImageResource(R.drawable.home_active_icon)
            R.id.chatbotIcon2 -> icon.setImageResource(R.drawable.chatbot_active_icon)
            R.id.mypageIcon2 -> icon.setImageResource(R.drawable.mypage_active_icon)
        }

        // 애니메이션
        moveBackIndicator(backIndicator, targetTab)
        applyAnimations(indicator, icon)
        moveIndicatorUp(indicator)
        label.setTextColor(ContextCompat.getColor(context, R.color.black))
    }

    private fun moveIndicatorUp(indicator: View) {
        // 목표 Y 위치 (위로 30dp 이동)
        val targetY = indicator.translationY - 37f
        val targetX=indicator.translationX - 20f

        // Y 위치 애니메이션
        val animatorY = ObjectAnimator.ofFloat(indicator, "translationY", targetY)
        // X 위치 애니메이션
        val animatorX = ObjectAnimator.ofFloat(indicator, "translationX", targetX)

        // 애니메이션 병합
        AnimatorSet().apply {
            playTogether(animatorY, animatorX) // X와 Y 이동 애니메이션 함께 실행
            duration = 300 // 애니메이션 지속 시간
            start()
        }

        Log.d("IndicatorAnimation", "Moving indicator up to Y: $targetY")
    }


    private fun deactivateTab(indicator: View, icon: ImageView, label: TextView) {
        indicator.visibility = View.INVISIBLE

        when (icon.id) {
            R.id.productIcon2 -> icon.setImageResource(R.drawable.product_icon)
            R.id.scannerIcon2 -> icon.setImageResource(R.drawable.scanner_icon)
            R.id.homeIcon2 -> icon.setImageResource(R.drawable.home_icon)
            R.id.chatbotIcon2 -> icon.setImageResource(R.drawable.chatbot_icon)
            R.id.mypageIcon2 -> icon.setImageResource(R.drawable.mypage_icon)
        }

        // Indicator를 원래 위치로 복귀 (X, Y 함께)
        val animatorY = ObjectAnimator.ofFloat(indicator, "translationY", 0f)
        val animatorX = ObjectAnimator.ofFloat(indicator, "translationX", 0f)

        val iconYAnimator = ObjectAnimator.ofFloat(icon, "translationY", 0f)
        val iconXAnimator = ObjectAnimator.ofFloat(icon, "translationX", 0f)

        AnimatorSet().apply {
            playTogether(animatorY, animatorX, iconYAnimator, iconXAnimator)
            duration = 300 // 애니메이션 지속 시간
            start()
        }

        label.setTextColor(ContextCompat.getColor(context, R.color.deactivity_text))
    }

    private fun deactivateAllExcept(
        activeIndicator: View,
        activeIcon: ImageView,
        activeLabel: TextView,
        tabs: List<Triple<LinearLayout, View, Triple<ImageView, TextView, Fragment>>>
    ) {
        tabs.forEach { (layout, indicator, content) ->
            val (icon, label, _) = content
            if (indicator != activeIndicator || icon != activeIcon || label != activeLabel) {
                deactivateTab(indicator, icon, label)
            }
        }
    }

    private fun moveBackIndicator(backIndicator: View, targetTab: View) {
        val targetPosition = targetTab.x + (targetTab.width / 2) - (backIndicator.width / 2)
        ObjectAnimator.ofFloat(backIndicator, "translationX", targetPosition).apply {
            duration = 300
            start()
        }
    }

    private fun applyAnimations(indicator: View, icon: ImageView) {
        val scaleX = ObjectAnimator.ofFloat(indicator, "scaleX", 0.5f, 1.3f)
        val scaleY = ObjectAnimator.ofFloat(indicator, "scaleY", 0.5f, 1.3f)
        val iconMoveUp = ObjectAnimator.ofFloat(icon, "translationY", 0f, -30f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, iconMoveUp)
            duration = 300
            start()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(mainFrameId, fragment)
            .addToBackStack(null)
            .commit()
    }
}
