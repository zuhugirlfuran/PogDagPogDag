package com.ssafy.snuggle_final_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.main.MainFragment
import com.ssafy.snuggle_final_app.main.NotificationActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                else -> false
            }
        }

        //툴바 메뉴 리스너
        val cartButton = findViewById<ImageButton>(R.id.app_bar_ib_cart)
        cartButton.setOnClickListener {
            Toast.makeText(this, "장바구니 버튼 클릭됨", Toast.LENGTH_SHORT).show()
        }

        val notificationButton = findViewById<ImageButton>(R.id.app_bar_ib_notification)
        notificationButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }


    // 프래그먼트 교체 함수
    private fun replaceFragment(mainFragment: MainFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, mainFragment)
            .commit()
    }
}
