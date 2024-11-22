package com.ssafy.snuggle_final_app.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.User
import com.ssafy.snuggle_final_app.data.model.response.UserResponse
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {

    // 유저 정보 가져오기
    private val _userInfo = MutableLiveData<UserResponse>()
    val userInfo: LiveData<UserResponse> get() = _userInfo

    fun getUserInfo(userId: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.getUserInfo(userId)
            }, { userRes ->
                if (userRes != null) {
                    _userInfo.value = userRes
                } else {
                    _userInfo.value = UserResponse(User())
                }

            }, { exception ->
                Log.e("Product", "새로운 상품 리스트 불러오기 오류: ${exception.message}")
//                _userInfo.value = emptyList()

            })
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