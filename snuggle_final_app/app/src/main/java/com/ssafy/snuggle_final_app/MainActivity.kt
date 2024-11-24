package com.ssafy.snuggle_final_app

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil.Companion.fcmService
import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.fcm.NotificationViewModel
import com.ssafy.snuggle_final_app.ui.chatbot.ChatBotFragment
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.ui.notification.NotificationActivity
import com.ssafy.snuggle_final_app.ui.product.ProductFragment
import com.ssafy.snuggle_final_app.ui.scanner.ScannerFragment
import com.ssafy.snuggle_final_app.util.PermissionChecker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null

    private val notificationViewModel: NotificationViewModel by viewModels()

    /** permission check **/
    private val checker = PermissionChecker(this)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val runtimePermissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    /** permission check **/

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

        //== nfc 설정 ==//
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            println("NFC를 지원하지 않는 장치입니다.")
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

        // 알림판으로 이동
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment, fragmentTag)
            .addToBackStack(fragmentTag)
            .commit()
    }

    // 권한
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermission() {
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener { //퍼미션 획득 성공일때
                init()
            }
            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Log.d("NFC_TAG", "태그 감지됨: ${tag.id?.joinToString("") { byte -> "%02X".format(byte) }}")

            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                val ndefMessage = ndef.ndefMessage
                if (ndefMessage != null) {
                    val records = ndefMessage.records
                    for (record in records) {
                        if (record.tnf == NdefRecord.TNF_WELL_KNOWN && record.type.contentEquals(
                                NdefRecord.RTD_TEXT
                            )
                        ) {
                            val text = parseTextRecord(record)
                            Log.d("NFC_TAG", "읽은 텍스트 데이터: $text")
                            sendNfcDataToFragment(text)
                        }
                    }
                } else {
                    Log.d("NFC_TAG", "NDEF 메시지가 비어 있음")
                    Log.d("NFC_TAG", "지원 기술 목록: ${tag.techList.joinToString()}")
                }
                ndef.close()
            } else {
                Log.d("NFC_TAG", "NDEF 형식을 지원하지 않는 태그")
            }
        }
    }


    private fun parseTextRecord(record: NdefRecord): String {
        val payload = record.payload
        val textEncoding =
            if ((payload[0].toInt() and 0x80) == 0) Charsets.UTF_8 else Charsets.UTF_16
        val languageCodeLength = payload[0].toInt() and 0x3F
        return String(
            payload,
            languageCodeLength + 1,
            payload.size - languageCodeLength - 1,
            textEncoding
        )
    }


    private fun sendNfcDataToFragment(payload: String) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_frameLayout)
        if (fragment is ScannerFragment) {
            Log.d("NFC_TAG", "ScannerFragment 활성화, handleNfcTag 호출")
            fragment.handleNfcTag(payload)
        } else {
            Log.d("NFC_TAG", "현재 Fragment는 ScannerFragment가 아닙니다.")
        }
    }


    private fun init() {
        initFCM()
        createNotificationChannel(BROAD_CHANNEL, "Broad Notifications")
        createNotificationChannel(DELIVERY_CHANNEL, "Delivery Notifications")
        subscribeToTopics()
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

    private fun subscribeToTopics() {
        // Broad Channel 구독
        FirebaseMessaging.getInstance().subscribeToTopic(BROAD_CHANNEL)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "BROAD_CHANNEL 구독 성공")
                    notificationViewModel.updateBroadSubscriptionStatus(true)
                } else {
                    Log.e(TAG, "BROAD_CHANNEL 구독 실패", task.exception)
                    notificationViewModel.updateBroadSubscriptionStatus(false)
                }
            }

        // Delivery Channel 구독
        FirebaseMessaging.getInstance().subscribeToTopic(DELIVERY_CHANNEL)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "DELIVERY_CHANNEL 구독 성공")
                    notificationViewModel.updateDeliverySubscriptionStatus(true)
                } else {
                    Log.e(TAG, "DELIVERY_CHANNEL 구독 실패", task.exception)
                    notificationViewModel.updateDeliverySubscriptionStatus(false)
                }
            }
    }

    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, name, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        // Notification Channel ID
        const val DELIVERY_CHANNEL = "delivery"
        const val BROAD_CHANNEL = "broad"

        fun uploadToken(token: String) {
            fcmService.uploadToken(token).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "FCM 토큰 업로드 성공")
                    } else {
                        Log.e(TAG, "FCM 토큰 업로드 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e(TAG, "FCM 토큰 업로드 실패: ${t.message}")
                }
            })
        }
    }
}

