package com.ssafy.snuggle_final_app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Notice
import com.ssafy.snuggle_final_app.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: DB 연결 -> 알림 데이터 받아와야 함
        // 임시 설정
        val dataList = listOf(
            
            Notice(
                R.drawable.notification_notice_ib,
                "새로운 기능 출시 안내2",
                "NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!2"
            ),
            Notice(
                R.drawable.notification_notice_ib,
                "새로운 기능 출시 안내2",
                "NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!2"
            ),
            Notice(
                R.drawable.notification_notice_ib,
                "새로운 기능 출시 안내2",
                "NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!2"
            ),
            Notice(
                R.drawable.notification_notice_ib,
                "새로운 기능 출시 안내2",
                "NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!2"
            ),
            Notice(
                R.drawable.notification_notice_ib,
                "새로운 기능 출시 안내2",
                "NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!2"
            ),
            Notice(
                R.drawable.notification_notice_ib,
                "새로운 기능 출시 안내2",
                "NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!2"
            ),
        )

        val adapter = NotificationAdapter(this, dataList)
        binding.notificationLv.adapter = adapter
    }
}