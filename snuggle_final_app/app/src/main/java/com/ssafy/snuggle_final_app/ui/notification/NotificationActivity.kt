package com.ssafy.snuggle_final_app.ui.notification

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.data.model.dto.Notice
import com.ssafy.snuggle_final_app.data.model.dto.Notification
import com.ssafy.snuggle_final_app.databinding.ActivityNotificationBinding
import com.ssafy.snuggle_final_app.fcm.NotificationViewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val channel = intent.getStringExtra("channel") ?: "broad"

        observeViewModel()

        // 임시 설정
        /*
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
*/
        val adapter = NotificationAdapter(this, emptyList())
        binding.notificationLv.adapter = adapter

        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId
        notificationViewModel.getNotificationsByUserId(userId)
    }

    private fun observeViewModel() {
        // 알림 데이터 관찰
        notificationViewModel.notifications.observe(this) { notifications ->
            notifications?.let {
                updateAdapter(it)
            }
        }

        // 오류 메시지 관찰
        notificationViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateAdapter(dataList: List<Notification>) {
        // 어댑터에 데이터 갱신
        adapter = NotificationAdapter(this, dataList.map { notice ->
            val iconImg = when (notice.channel) {
                "delivery" -> R.drawable.notification_deliver_ib // 배송 채널 아이콘
                "broad" -> R.drawable.notification_notice_ib       // 일반 알림 채널 아이콘
                else -> R.drawable.notification_notice_ib // 기본 아이콘
            }

            Notice(
                img = iconImg,
                title = notice.title,
                subTitle = notice.content
            )
        })
        binding.notificationLv.adapter = adapter
    }
}