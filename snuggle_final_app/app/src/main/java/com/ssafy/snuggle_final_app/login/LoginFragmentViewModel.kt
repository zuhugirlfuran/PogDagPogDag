package com.ssafy.snuggle_final_app.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.User
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class LoginFragmentViewModel: ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

    private val _isAvailableId = MutableLiveData<Boolean>()
    val isAvailableId: LiveData<Boolean> get() = _isAvailableId

    private val _joinStatus = MutableLiveData<Boolean>()
    val joinStatus: LiveData<Boolean> get() = _joinStatus

    // 로그인 처리 함수
    fun login(id: String, pass: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.userService.login(User(id, pass))
            }.onSuccess {
                _user.value = it
            }.onFailure {
                _user.value = User()
            }
        }
    }

    // ID 중복 확인 함수
    fun checkAvailableId(id: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.isUsedId(id)
            }, { isUsed ->
                _isAvailableId.value = !isUsed
                Log.e("ID Check", "ID 중복: $isUsed")
            }, {
                _isAvailableId.value = false
                Log.e("ID Check", "ID 중복 확인 실패: ${it.message}")
            })
        }
    }

    // 회원가입 함수
    fun join(user: User) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.insert(user)
            }, { isInserted ->
                Log.d("Join", "회원가입 성공")
                _joinStatus.value = isInserted // 성공 여부 저장
            }, {
                _joinStatus.value = false
                Log.e("Join", "회원가입 실패: ${it.message}")
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
