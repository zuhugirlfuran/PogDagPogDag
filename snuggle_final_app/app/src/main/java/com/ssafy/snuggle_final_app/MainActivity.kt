package com.ssafy.snuggle_final_app

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
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
    private var nfcAdapter: NfcAdapter? = null

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

        if (currentFragment == null) { // 동일한 태그의 프래그먼트가 없을 때만 추가
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit()
        }
    }

    fun fragmentBackPressed(
        lifecycleOwner: androidx.lifecycle.LifecycleOwner,
        callback: OnBackPressedCallback
    ) {
        onBackPressedDispatcher.addCallback(lifecycleOwner, callback)
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
                        if (record.tnf == NdefRecord.TNF_WELL_KNOWN && record.type.contentEquals(NdefRecord.RTD_TEXT)) {
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
        val textEncoding = if ((payload[0].toInt() and 0x80) == 0) Charsets.UTF_8 else Charsets.UTF_16
        val languageCodeLength = payload[0].toInt() and 0x3F
        return String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, textEncoding)
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





}