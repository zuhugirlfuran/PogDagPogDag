package com.ssafy.snuggle_final_app

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.snuggle_final_app.data.local.SharedPreferencesUtil
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil.Companion.fcmService
import com.ssafy.snuggle_final_app.databinding.ActivityMainBinding
import com.ssafy.snuggle_final_app.fcm.NotificationViewModel
import com.ssafy.snuggle_final_app.ui.chatbot.ChatBotFragment
import com.ssafy.snuggle_final_app.ui.main.MainFragment
import com.ssafy.snuggle_final_app.ui.mypage.CouponFragment
import com.ssafy.snuggle_final_app.ui.mypage.MypageFragment
import com.ssafy.snuggle_final_app.ui.navigation.BottomNavigationHelper
import com.ssafy.snuggle_final_app.ui.notification.NotificationActivity
import com.ssafy.snuggle_final_app.ui.product.ProductFragment
import com.ssafy.snuggle_final_app.ui.scanner.ScannerFragment
import com.ssafy.snuggle_final_app.util.PermissionChecker
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null

    private val notificationViewModel: NotificationViewModel by viewModels()

    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

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

        // Beacon관련
        setBeacon()

        sharedPreferencesUtil = SharedPreferencesUtil(this)

        // 권한 체크
        checkPermission()

        // 초기 프래그먼트 설정 (MainFragment)
        addToStackFragment(MainFragment())

        // BottomNavigationHelper 초기화 및 설정
        val bottomNavigationHelper = BottomNavigationHelper(
            this,
            supportFragmentManager,
            R.id.main_frameLayout
        )

        val backIndicator = binding.customBottomNavigation.backIndicator2
        val homeTab = binding.customBottomNavigation.home

        val tabs = listOf(
            Triple(
                binding.customBottomNavigation.product,
                binding.customBottomNavigation.productIndicator2,
                Triple(
                    binding.customBottomNavigation.productIcon2,
                    binding.customBottomNavigation.productLabel2,
                    ProductFragment()
                )
            ),
            Triple(
                binding.customBottomNavigation.scanner,
                binding.customBottomNavigation.scannerIndicator2,
                Triple(
                    binding.customBottomNavigation.scannerIcon2,
                    binding.customBottomNavigation.scannerLabel2,
                    ScannerFragment()
                )
            ),
            Triple(
                binding.customBottomNavigation.home,
                binding.customBottomNavigation.homeIndicator2,
                Triple(
                    binding.customBottomNavigation.homeIcon2,
                    binding.customBottomNavigation.homeLabel2,
                    MainFragment()
                )
            ),
            Triple(
                binding.customBottomNavigation.chatbot,
                binding.customBottomNavigation.chatbotIndicator2,
                Triple(
                    binding.customBottomNavigation.chatbotIcon2,
                    binding.customBottomNavigation.chatbotLabel2,
                    ChatBotFragment()
                )
            ),
            Triple(
                binding.customBottomNavigation.mypage,
                binding.customBottomNavigation.mypageIndicator2,
                Triple(
                    binding.customBottomNavigation.mypageIcon2,
                    binding.customBottomNavigation.mypageLabel2,
                    MypageFragment()
                )
            )
        )

        bottomNavigationHelper.setup(
            backIndicator = binding.customBottomNavigation.backIndicator2,
            tabs = tabs,
            defaultTab = binding.customBottomNavigation.home
        )

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
        // Post Notifications 권한 체크
        val notificationPermissionGranted = checker.checkPermission(this, runtimePermissions)
        // Beacon 관련 권한 체크
        val beaconPermissionGranted = checker.checkPermission(this, beaconRuntimePermission)

        if (!notificationPermissionGranted || !beaconPermissionGranted) {
            checker.setOnGrantedListener {
                // 모든 권한 획득 후 초기화 및 스캔 시작
                init()
                startScan()
            }

            // 요청할 권한 그룹 설정
            val permissionsToRequest = mutableListOf<String>()
            if (!notificationPermissionGranted) {
                permissionsToRequest.addAll(runtimePermissions)
            }
            if (!beaconPermissionGranted) {
                permissionsToRequest.addAll(beaconRuntimePermission)
            }

            // 필요한 권한 요청
            checker.requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            // 이미 모든 권한이 있는 경우
            init()
            startScan()
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

    // Beacon 관련
    private val BEACON_UUID = "fda50693-a4e2-4fb1-afcf-c6eb07647825" // 우리반은 모두 동일.
    private val BEACON_MAJOR = "10004"  // 우리반은 모두 동일.
    private val BEACON_MINOR = "54480"  // 우리반은 모두 동일.
    private val BLUETOOTH_ADDRESS = "00:81:F9:E2:45:74"
    private val BEACON_DISTANCE = 10.0 //거리

    private val beaconRuntimePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    // Beacon의 Region 설정
    private val region = Region(
        "estimote",
        listOf(
            Identifier.parse(BEACON_UUID),
            Identifier.parse(BEACON_MAJOR),
            Identifier.parse(BEACON_MINOR)
        ),
        BLUETOOTH_ADDRESS
    )

    private fun isYourBeacon(beacon: Beacon): Boolean {
        return (beacon.id2.toString() == BEACON_MAJOR &&
                beacon.id3.toString() == BEACON_MINOR &&
                beacon.distance <= BEACON_DISTANCE
                )
    }

    private lateinit var beaconManager: BeaconManager
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private fun setBeacon(){
        //BeaconManager 지정
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    val handler = Handler(Looper.getMainLooper())
    private fun startScan() {
        // 블루투스 Enable 확인
        if (!bluetoothAdapter.isEnabled) {
            requestEnableBLE()
            Log.d(TAG, "startScan: 블루투스가 켜지지 않았습니다.")
            return
        }

        //detacting되는 해당 region의 beacon정보를 받는 클래스 지정.
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.startRangingBeacons(region)

        handler.postDelayed({
            stopScan()
        }, 30_000)
    }

    private fun stopScan() {
        Log.d(TAG, "stopScan: 비콘 스캔 종료")
        beaconManager.stopMonitoring(region)
        beaconManager.stopRangingBeacons(region)
    }

    private fun requestEnableBLE() {
        val callBLEEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestBLEActivity.launch(callBLEEnableIntent)
        Log.d(TAG, "requestEnableBLE: ")
    }

    private val requestBLEActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // 사용자의 블루투스 사용이 가능한지 확인
        if (bluetoothAdapter.isEnabled) {
            startScan()
        }
    }

    var rangeNotifier: RangeNotifier = object : RangeNotifier {
        override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
            Log.d(TAG, "didRangeBeaconsInRegion: $beacons")
            beacons?.run {
                forEach { beacon ->
                    // Major, Minor로 Beacon 구별
                    if (isYourBeacon(beacon)) {
                        distance = beacon.distance
                        // 한번만 띄우기 위한 조건
                        if(distance <= 5.0){
                            Log.d(TAG, "didRangeBeaconsInRegion: ${BLUETOOTH_ADDRESS}")
                            Log.d(TAG, "didRangeBeaconsInRegion: 비콘과의 거리 ${distance}")

                            if (sharedPreferencesUtil.isTodayEventDate()) {
                                // 이미 이벤트가 실행된 기록이 있기 때문에 아무것도 하지 않음
                            } else {
                                // 오늘 날짜로 이벤트를 저장하고 이벤트 실행
                                showEventDialog() // 이벤트 다이얼로그 1회만 호출
                                sharedPreferencesUtil.saveCurrentDate()
                            }

                            stopScan()  // BLE 스캔 종료
                        }

                    }
                    Log.d(TAG, "distance: " + beacon.distance + " id:" + beacon.id1 + "/" + beacon.id2 + "/" + beacon.id3
                    )
                }

                if (beacons.isEmpty()) {
                    Log.d(TAG, "didRangeBeaconsInRegion: 비콘 찾기 실패")
                    // 비콘 찾기 실패
                }
            }
        }
    }

    fun showEventDialog() {
        Log.d(TAG, "showEventDialog: Attempting to display dialog")
        if (!isFinishing && !isDestroyed) { // Activity가 유효한지 확인
            Handler(Looper.getMainLooper()).post {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.apply {
                    setTitle("쿠폰 알림") // 다이얼로그 제목
                    setMessage("쿠폰이 있습니다. 확인하시겠습니까?") // 다이얼로그 메시지
                    setCancelable(true) // 뒤로가기나 터치로 취소 가능 여부
                    setPositiveButton("확인") { dialog, _ ->
                        replaceFragment(CouponFragment())
                        dialog.dismiss() // 확인 버튼 클릭 시 다이얼로그 닫기
                    }
                    setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss() // 취소 버튼 클릭 시 다이얼로그 닫기
                    }
                }
                builder.create().show()
            }
        } else {
            Log.d(TAG, "Activity is not valid for showing a dialog.")
        }
    }


    companion object {

        var distance: Double = 19.0

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

