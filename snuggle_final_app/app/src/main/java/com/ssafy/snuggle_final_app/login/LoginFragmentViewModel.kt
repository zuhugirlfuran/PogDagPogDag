package com.ssafy.snuggle_final_app.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.dto.User
import com.ssafy.snuggle_final_app.util.RetrofitUtil
import kotlinx.coroutines.launch

class LoginFragmentViewModel: ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _isAvailableId = MutableLiveData<Boolean>()
    val isAvailableId: LiveData<Boolean>
        get() = _isAvailableId

    fun login(id: String, pass: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.userService.login(User(id, pass))
            }.onSuccess { user ->
                // 로그인 성공 시
                _user.value = user
                // 로그 출력
                Log.d("Login", "로그인 성공: ${user.userId}")
            }.onFailure { throwable ->
                // 로그인 실패 시
                _user.value = User()
                // 로그 출력
                Log.e("Login", "로그인 실패: ${throwable.message}")
            }
        }
    }


    fun checkAvailableId(id: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.userService.isUsedId(id)
            }.onSuccess { isUsed ->
                _isAvailableId.value = !isUsed
            }.onFailure {
                _isAvailableId.value = false
            }
        }
    }

    fun join(id:String, name:String, pass:String) {
        viewModelScope.launch {
            RetrofitUtil.userService.insert(User(id, name, pass))
        }
    }

}