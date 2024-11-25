package com.ssafy.snuggle_final_app.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.data.model.dto.Coupon
import com.ssafy.snuggle_final_app.data.model.response.UserResponse
import com.ssafy.snuggle_final_app.data.service.CouponService
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "MyPageViewModel_싸피"

class MyPageViewModel : ViewModel() {

    // 유저 정보 가져오기
    private val _userInfo = MutableLiveData<UserResponse?>()
    val userInfo: MutableLiveData<UserResponse?> get() = _userInfo

    fun getUserInfo(userId: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.getUserInfo(userId)
            }, { userRes ->

                Log.d(TAG, "API Success: $userRes")
                _userInfo.value = userRes

            }, { exception ->
                Log.e(TAG, "API Exception: ${exception.message}")
            })
        }
    }

    // 쿠폰 정보 가져오기
    private val _coupons = MutableLiveData<List<Coupon>>()
    val coupons: LiveData<List<Coupon>> get() = _coupons

    fun getCoupons(userId: String) {
        viewModelScope.launch {
            try {
                // ApplicationClass의 couponService를 통해 API 호출
                val couponList = ApplicationClass.couponService.getCouponByUserId(userId)
                _coupons.value = couponList
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching coupons: ${e.message}")
            }
        }
    }


    // 공통으로 API 호출을 안전하게 처리하는 함수
    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
        onSuccess: (T) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            val result = apiCall()
            onSuccess(result)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

}