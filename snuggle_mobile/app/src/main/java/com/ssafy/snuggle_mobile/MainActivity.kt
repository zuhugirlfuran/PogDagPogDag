package com.ssafy.snuggle_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.snuggle_mobile.databinding.ActivityMainBinding
import com.ssafy.snuggle_mobile.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                else -> false
            }
        }
    }

    private fun replaceFragment(mainFragment: MainFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }
}