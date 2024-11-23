package com.ssafy.snuggle_final_app

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil.Companion.fcmService
import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.main.MainFragment
import com.ssafy.snuggle_final_app.main.NotificationActivity
import com.ssafy.snuggle_final_app.ui.chatbot.ChatBotFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.ui.product.ProductFragment
import com.ssafy.snuggle_final_app.ui.scanner.ScannerFragment
import com.ssafy.snuggle_final_app.util.PermissionChecker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.FirebaseApp

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    /** permission check **/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 권한 체크
        checkPermission()

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
            replaceFragment(com.ssafy.snuggle_final_app.ui.cart.CartFragment())
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
//        val currentFragment = supportFragmentManager.findFragmentByTag(fragmentTag)

//        if (currentFragment != null) {
//            Log.d("FragmentTransaction", "Adding fragment: ${fragment.javaClass.simpleName} ${currentFragment.tag}")
//            return
//        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment, fragmentTag)
            .addToBackStack(fragmentTag)
            .commit()
//        }
    }

    // 권한
    private fun checkPermission() {
        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener { //퍼미션 획득 성공일때
                init()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            init()
        }
        /** permission check **/
    }

    private fun init() {
        initFCM()
        createNotificationChannel(channel_id, "snuggle")
    }

    private fun initFCM() {

        // FCM 토큰 수신
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }
            // token log 남기기
            Log.d(TAG, "token: ${task.result ?: "task.result is null"}")
            if (task.result != null) {
                uploadToken(task.result!!)
            }
        })
    }

    // Notification 수신을 위한 체널 추가
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(id, name, importance))
        }
    }

    companion object {
        // Notification Channel ID
        const val channel_id = "SSAFY_FINAL"

        // ratrofit  수업 후 network 에 업로드 할 수 있도록 구성
        fun uploadToken(token: String) {
            // 새로운 토큰 수신 시 서버로 전송
            fcmService.uploadToken(token).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val res = response.body()
                        Log.d(TAG, "onResponse: $res")
                    } else {
                        Log.d(TAG, "onResponse: Error Code ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(TAG, t.message ?: "토큰 정보 등록 중 통신오류")
                }
            })
        }
    }


}