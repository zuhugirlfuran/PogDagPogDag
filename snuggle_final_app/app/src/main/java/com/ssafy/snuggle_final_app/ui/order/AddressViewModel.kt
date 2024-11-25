package com.ssafy.snuggle_final_app.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Address
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "AddressViewModel"

class AddressViewModel : ViewModel() {

    private val _insertedAddress = MutableLiveData<Int>()
    val insertedAddress: LiveData<Int> get() = _insertedAddress

    fun insertAddress(address: Address) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.addressService.insertAddress(address)
            }, { addressId ->
                if (addressId > 0) {
                    _insertedAddress.value = addressId
                    // 추가된 주소 데이터 가져오기
                    getAddress(address.userId)
                }
            }, {
                Log.d(TAG, "insertAddress: 주소 추가 실패")
            })
        }
    }

    private val _isAddressExists = MutableLiveData<Boolean>()
    val isAddressExists: LiveData<Boolean> get() = _isAddressExists

    fun isExist(userId: String) {
        if (_isAddressExists.value != null) return // 이미 호출된 경우 실행하지 않음

        viewModelScope.launch {
            Log.d(TAG, "isExist: API 호출 시작, userId=$userId")
            safeApiCall({
                RetrofitUtil.addressService.isExist(userId)
            }, { isExisted ->
                _isAddressExists.value = isExisted
                Log.d(TAG, "isExist: 유저의 주소 정보 여부 성공 $isExisted")
            }, { error ->
                Log.e(TAG, "isExist: 유저의 주소 정보 여부 실패", error)
            })
        }
    }


    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> get() = _address
    fun getAddress(userId: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.addressService.getAddress(userId)
            }, { data ->
                _address.value = data
                Log.d(TAG, "getAddress: 유저의 주소 데이터 불러오기 성공 $data")
            }, {
                Log.d(TAG, "getAddress: 유저의 주소 데이터 불러오기 실패")
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