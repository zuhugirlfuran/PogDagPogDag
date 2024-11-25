package com.ssafy.snuggle_final_app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.ui.product.ProductFragment
import com.ssafy.snuggle_final_app.ui.scanner.ScannerFragment
import com.ssafy.snuggle_final_app.ui.chatbot.ChatBotFragment as ChatBotFragment1


class Bottom_navi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backIndicator = findViewById<View>(R.id.backIndicator2)
        backIndicator.visibility = View.INVISIBLE

        val homeIndicator = findViewById<View>(R.id.homeIndicator2)
        val homeIcon = findViewById<ImageView>(R.id.homeIcon2)
        val homeLabel = findViewById<TextView>(R.id.homeLabel2)
        val homeTab = findViewById<LinearLayout>(R.id.home)

        homeTab.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // backIndicator를 homeTab 중심에 위치시킴
                val targetPosition = homeTab.x + (homeTab.width / 2) - (backIndicator.width / 2)
                backIndicator.translationX = targetPosition

                // backIndicator 보이게 설정
                backIndicator.visibility = View.VISIBLE

                // Listener 제거
                homeTab.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        setupBottomNavigation()
        activateTab(homeIndicator, homeIcon, homeLabel)
//        moveBackIndicator(homeTab)
    }

    private fun setupBottomNavigation() {
        // 제품 버튼
        val productTab = findViewById<LinearLayout>(R.id.product)
        val productIndicator = findViewById<View>(R.id.productIndicator2)
        val productIcon = findViewById<ImageView>(R.id.productIcon2)
        val productLabel = findViewById<TextView>(R.id.productLabel2)

        // 스캐너 버튼
        val scannerTab = findViewById<LinearLayout>(R.id.scanner)
        val scannerIndicator = findViewById<View>(R.id.scannerIndicator2)
        val scannerIcon = findViewById<ImageView>(R.id.scannerIcon2)
        val scannerLabel = findViewById<TextView>(R.id.scannerLabel2)

        // 홈 버튼
        val homeTab = findViewById<LinearLayout>(R.id.home)
        val homeIndicator = findViewById<View>(R.id.homeIndicator2)
        val homeIcon = findViewById<ImageView>(R.id.homeIcon2)
        val homeLabel = findViewById<TextView>(R.id.homeLabel2)

        // 챗봇 버튼
        val chatbotTab = findViewById<LinearLayout>(R.id.chatbot)
        val chatbotIndicator = findViewById<View>(R.id.chatbotIndicator2)
        val chatbotIcon = findViewById<ImageView>(R.id.chatbotIcon2)
        val chatbotLabel = findViewById<TextView>(R.id.chatbotLabel2)

        // 마이페이지 버튼
        val mypageTab = findViewById<LinearLayout>(R.id.mypage)
        val mypageIndicator = findViewById<View>(R.id.mypageIndicator2)
        val mypageIcon = findViewById<ImageView>(R.id.mypageIcon2)
        val mypageLabel = findViewById<TextView>(R.id.mypageLabel2)

        // Set click listeners for each tab
        productTab.setOnClickListener {
            activateTab(productIndicator, productIcon, productLabel)
            deactivateAllExcept(productIndicator, productIcon, productLabel)
            moveBackIndicator(productTab)
            replaceFragment(ProductFragment())

        }

        scannerTab.setOnClickListener {
            activateTab(scannerIndicator, scannerIcon, scannerLabel)
            deactivateAllExcept(scannerIndicator, scannerIcon, scannerLabel)
            moveBackIndicator(scannerTab)
            replaceFragment(ScannerFragment())

        }

        homeTab.setOnClickListener {
            activateTab(homeIndicator, homeIcon, homeLabel)
            deactivateAllExcept(homeIndicator, homeIcon, homeLabel)
            moveBackIndicator(homeTab)
            replaceFragment(MainFragment())

        }

        chatbotTab.setOnClickListener {
            activateTab(chatbotIndicator, chatbotIcon, chatbotLabel)
            deactivateAllExcept(chatbotIndicator, chatbotIcon, chatbotLabel)
            moveBackIndicator(chatbotTab)
            replaceFragment(ChatBotFragment1())

        }

        mypageTab.setOnClickListener {
            activateTab(mypageIndicator, mypageIcon, mypageLabel)
            deactivateAllExcept(mypageIndicator, mypageIcon, mypageLabel)
            moveBackIndicator(mypageTab)
            replaceFragment(MypageFragment())

        }
    }

    private fun activateTab(indicator: View, icon: ImageView, label: TextView) {
        indicator.visibility = View.VISIBLE
        val backIndicator = findViewById<View>(R.id.backIndicator2)
        backIndicator.visibility = View.VISIBLE


        when (icon.id) {
            R.id.productIcon2 -> icon.setImageResource(R.drawable.product_active_icon)
            R.id.scannerIcon2 -> icon.setImageResource(R.drawable.scanner_active_icon)
            R.id.homeIcon2 -> icon.setImageResource(R.drawable.home_active_icon)
            R.id.chatbotIcon2 -> icon.setImageResource(R.drawable.chatbot_active_icon)
            R.id.mypageIcon2 -> icon.setImageResource(R.drawable.mypage_active_icon)
        }

        // Animate indicator (View), icon (ImageView), and label (TextView)
        val indicatorMoveUp =
            ObjectAnimator.ofFloat(indicator, "translationY", 0f, -40f) // Indicator도 위로 이동
        val indicatorMoveLeft = ObjectAnimator.ofFloat(indicator, "translationX", 0f, -20f)
        val iconMoveUp = ObjectAnimator.ofFloat(icon, "translationY", 0f, -30f)
//        val labelMoveUp = ObjectAnimator.ofFloat(label, "translationY", 0f, -30f)

        val backIndicatorMoveUp =
            ObjectAnimator.ofFloat(backIndicator, "translationY", 0f, -40f)


        // Scale animation for Indicator
        val scaleX = ObjectAnimator.ofFloat(indicator, "scaleX", 0.5f, 1.3f)
        val scaleY = ObjectAnimator.ofFloat(indicator, "scaleY", 0.5f, 1.3f)

        // Combine animations
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            scaleX, scaleY, // 크기 변화
            indicatorMoveUp, indicatorMoveLeft, // 위와 왼쪽으로 이동
            iconMoveUp,
            backIndicatorMoveUp
//            labelMoveUp // 아이콘과 라벨 이동
        )
        animatorSet.duration = 300
        animatorSet.start()

        // Change colors
//        icon.setColorFilter(ContextCompat.getColor(this, R.color.red))
        label.setTextColor(ContextCompat.getColor(this, R.color.black))
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

        // Animate back to original position
        val indicatorMoveDown =
            ObjectAnimator.ofFloat(indicator, "translationY", indicator.translationY, 0f)
        val iconMoveDown = ObjectAnimator.ofFloat(icon, "translationY", icon.translationY, 0f)
        val labelMoveDown = ObjectAnimator.ofFloat(label, "translationY", label.translationY, 0f)

        val scaleX = ObjectAnimator.ofFloat(indicator, "scaleX", 1f, 1.5f) // 크기 증가
        val scaleY = ObjectAnimator.ofFloat(indicator, "scaleY", 1f, 1.5f) // 크기 증가


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(indicatorMoveDown, iconMoveDown, labelMoveDown)
        animatorSet.duration = 300
        animatorSet.start()

        // Change colors
//        icon.setColorFilter(ContextCompat.getColor(this, R.color.black))
        label.setTextColor(ContextCompat.getColor(this, R.color.deactivity_text))
    }


    private fun deactivateAllExcept(
        activeIndicator: View,
        activeIcon: ImageView,
        activeLabel: TextView
    ) {
        val allTabs = listOf(
            Triple(
                findViewById<View>(R.id.productIndicator2),
                findViewById<ImageView>(R.id.productIcon2),
                findViewById<TextView>(R.id.productLabel2)
            ),
            Triple(
                findViewById<View>(R.id.scannerIndicator2),
                findViewById<ImageView>(R.id.scannerIcon2),
                findViewById<TextView>(R.id.scannerLabel2)
            ),
            Triple(
                findViewById<View>(R.id.homeIndicator2),
                findViewById<ImageView>(R.id.homeIcon2),
                findViewById<TextView>(R.id.homeLabel2)
            ),
            Triple(
                findViewById<View>(R.id.chatbotIndicator2),
                findViewById<ImageView>(R.id.chatbotIcon2),
                findViewById<TextView>(R.id.chatbotLabel2)
            ),
            Triple(
                findViewById<View>(R.id.mypageIndicator2),
                findViewById<ImageView>(R.id.mypageIcon2),
                findViewById<TextView>(R.id.mypageLabel2)
            )
        )

        for (tab in allTabs) {
            if (tab.first != activeIndicator || tab.second != activeIcon || tab.third != activeLabel) {
                deactivateTab(tab.first, tab.second, tab.third)
            }
        }
    }

    private fun moveBackIndicator(targetTab: View) {
        val backIndicator = findViewById<View>(R.id.backIndicator2)

        // 목표 위치 계산
        val targetPosition = targetTab.x + (targetTab.width / 2) - (backIndicator.width / 2)

        // 애니메이션 설정
        val animator = ObjectAnimator.ofFloat(backIndicator, "translationX", targetPosition)
        animator.duration = 0 // 애니메이션 지속 시간
        animator.start()

        Log.d("BackIndicator", "Target X: $targetPosition")
        Log.d("BackIndicator", "Current X: ${backIndicator.translationX}")
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment)
            .commit()
    }
}
