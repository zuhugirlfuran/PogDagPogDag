package com.ssafy.snuggle_final_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.snuggle_final_app.cart.CartFragment
import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.main.MainFragment
import com.ssafy.snuggle_final_app.main.NotificationActivity
import com.ssafy.snuggle_final_app.scanner.ScannerFragment
import com.ssafy.snuggle_final_app.mypage.MypageFragment
import com.ssafy.snuggle_final_app.product.ProductFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 초기 프래그먼트 설정 (MainFragment)
        replaceFragment(MainFragment())

        // 네비게이션 설정
        binding.bottomNavigation.selectedItemId = R.id.home
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
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
                R.id.scanner -> {
                    replaceFragment(ScannerFragment())
                    true
                }
                else -> false
            }
        }

        //툴바 메뉴 리스너
        val cartButton = findViewById<ImageButton>(R.id.app_bar_ib_cart)
        cartButton.setOnClickListener {
            replaceFragment(CartFragment())
        }

        val notificationButton = findViewById<ImageButton>(R.id.app_bar_ib_notification)
        notificationButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        val logo = findViewById<ImageView>(R.id.app_bar_iv_logo)
        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment)
            .commit()
    }
}