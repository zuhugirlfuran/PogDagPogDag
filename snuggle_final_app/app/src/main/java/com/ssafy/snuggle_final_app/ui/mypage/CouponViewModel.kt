package com.ssafy.snuggle_final_app.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.data.model.dto.Coupon
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch
import kotlin.math.log

private const val TAG = "CouponViewModel"
class CouponViewModel : ViewModel() {



    private val _isCouponAdded = MutableLiveData<Boolean>()
    val isCouponAdded: LiveData<Boolean> get() = _isCouponAdded

    fun insertCoupon(coupon: Coupon) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.couponService.insertCoupon(coupon)
            }, { isCouponAdded ->
                _isCouponAdded.value = isCouponAdded > 0
                Log.d(TAG, "insertCoupon: 쿠폰 데이터 추가 성공 $isCouponAdded")
            }, {
                Log.d(TAG, "insertCoupon: 쿠폰 데이터 추가 실패")
            })
        }
    }

    // 쿠폰 정보 가져오기
    private val _couponList = MutableLiveData<List<Coupon>>()
    val couponList: LiveData<List<Coupon>> get() = _couponList
    fun getCoupons(userId: String) {
        viewModelScope.launch {
            try {
                // ApplicationClass의 couponService를 통해 API 호출
                val couponList = RetrofitUtil.couponService.getCouponByUserId(userId)
                _couponList.value = couponList
                Log.d(TAG, "getCoupons: ${couponList}")
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