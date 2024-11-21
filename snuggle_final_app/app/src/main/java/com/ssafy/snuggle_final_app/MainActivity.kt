package com.ssafy.snuggle_final_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.main.MainFragment
import com.ssafy.snuggle_final_app.product.ProductFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 프래그먼트 설정 (MainFragment)
        replaceFragment(MainFragment())

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
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment)
            .commit()
    }
}