package com.ssafy.snuggle_final_app.fcm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Notification
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "NotificationViewModel"

class NotificationViewModel : ViewModel() {

    private val _isBroadSubscribed = MutableLiveData<Boolean>(false)
    val isBroadSubscribed: LiveData<Boolean> get() = _isBroadSubscribed

    private val _isDeliverySubscribed = MutableLiveData<Boolean>(false)
    val isDeliverySubscribed: LiveData<Boolean> get() = _isDeliverySubscribed

    fun updateBroadSubscriptionStatus(isSubscribed: Boolean) {
        _isBroadSubscribed.value = isSubscribed
    }

    fun updateDeliverySubscriptionStatus(isSubscribed: Boolean) {
        _isDeliverySubscribed.value = isSubscribed
    }


    private val _isNotificationSaved = MutableLiveData<Boolean>()
    val isNotificationSaved: LiveData<Boolean> get() = _isNotificationSaved

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    companion object {
        private const val TAG = "NotificationViewModel"
    }

    fun getNotificationsByUserId(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitUtil.notificationService.getNotificationByUserId(userId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _notifications.value = response.body()
                        Log.d(TAG, "Notifications fetched successfully")
                    } else {
                        _errorMessage.value = "Error: ${response.code()}"
                        Log.e(
                            TAG,
                            "Failed to fetch notifications: ${response.errorBody()?.string()}"
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message ?: "Unknown error"
                    Log.e(TAG, "Error fetching notifications: ${e.message}")
                }
            }
        }
    }
}