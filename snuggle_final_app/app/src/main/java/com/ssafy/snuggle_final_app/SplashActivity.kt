package com.ssafy.snuggle_final_app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

//        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        startActivity(Intent(this, LoginActivity::class.java))
//        finish() // 스플래시 화면 종료

        // 첫 번째 ImageView에 애니메이션 설정
        val splashBackground1 = findViewById<ImageView>(R.id.splash_background1)
        Glide.with(this)
            .load(R.raw.falling_snow)
            .override(560, 560)
            .into(splashBackground1)

        // 두 번째 ImageView에 애니메이션 설정
        val splashBackground2 = findViewById<ImageView>(R.id.splash_background2)
        Glide.with(this)
            .load(R.raw.falling_snow)
            .override(560, 560)
            .into(splashBackground2)

        // 3초 후 메인 화면으로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // 스플래시 종료
        }, 3000) // 3000ms (3초)

    }
}