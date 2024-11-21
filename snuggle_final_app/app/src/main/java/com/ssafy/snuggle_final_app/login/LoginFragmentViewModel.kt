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

    // 로그인 처리 함수
    fun login(id: String, pass: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.login(User(id, pass))
            }, { user ->
                _user.value = user
                Log.d("Login", "로그인 성공: ${user.userId}")
            }, {
                _user.value = User()
                Log.e("Login", "로그인 실패: ${it.message}")
            })
        }
    }

    // ID 중복 확인 함수
    fun checkAvailableId(id: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.isUsedId(id)
            }, { isUsed ->
                _isAvailableId.value = !isUsed
            }, {
                _isAvailableId.value = false
                Log.e("ID Check", "ID 중복 확인 실패: ${it.message}")
            })
        }
    }

    // 회원가입 함수
    fun join(id: String, name: String, pass: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.insert(User(id, name, pass))
            }, {
                Log.d("Join", "회원가입 성공")
            }, {
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
