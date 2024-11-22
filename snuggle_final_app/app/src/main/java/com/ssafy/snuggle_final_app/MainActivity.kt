package com.ssafy.snuggle_final_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.cart.CartFragment
import com.ssafy.snuggle_final_app.chatbot.ChatBotFragment

import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.main.MainFragment
import com.ssafy.snuggle_final_app.main.NotificationActivity
import com.ssafy.snuggle_final_app.mypage.MypageFragment
import com.ssafy.snuggle_final_app.product.ProductFragment
import com.ssafy.snuggle_final_app.scanner.ScannerFragment
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 프래그먼트 설정 (MainFragment)
        addToStackFragment(MainFragment())

        val bottomNavigation = binding.bottomNavigation
        bottomNavigation.selectedItemId = R.id.home

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(MainFragment())
                    true
                }

                R.id.product -> {
                    replaceFragment(ProductFragment())
                    true
                }

                R.id.mypage -> {
                    replaceFragment(MypageFragment())
                    true
                }

                R.id.chatbot -> {
                    addToStackFragment(ChatBotFragment())
                    true
                }

                R.id.scanner -> {
                    replaceFragment(ScannerFragment())
                    true
                }

                else -> false
            }
        }

        // appBar_cart 버튼 클릭 리스너 설정
        val cartButton = findViewById<ImageButton>(R.id.app_bar_ib_cart)
        cartButton.setOnClickListener {
            replaceFragment(CartFragment())
        }

        // appBar logo 클릭 시 메인 프레그먼트로 이동
        val logoBtn = findViewById<ImageView>(R.id.app_bar_iv_logo)
        logoBtn.setOnClickListener {
            replaceFragment(MainFragment())
        }

        val notificationBtn = findViewById<ImageButton>(R.id.app_bar_ib_notification)
        notificationBtn.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment)
            .commit()
    }


    fun addToStackFragment(fragment: Fragment) {
        val fragmentTag = fragment.javaClass.simpleName
        val currentFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        // 현재 보여지고 있는 Fragment와 새로 추가하려는 Fragment가 동일한지 확인
//        if (currentFragment != null && currentFragment::class.java == fragment::class.java) {
//            return // 동일한 Fragment를 다시 추가하지 않음
//        }
        if (currentFragment != null) {
            Log.d("FragmentTransaction", "Adding fragment: ${fragment.javaClass.simpleName} ${currentFragment.tag}")
            return
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit()
//        }
    }

    fun fragmentBackPressed(
        lifecycleOwner: androidx.lifecycle.LifecycleOwner,
        callback: OnBackPressedCallback
    ) {
        onBackPressedDispatcher.addCallback(lifecycleOwner, callback)
    }

}