package com.ssafy.snuggle_final_app.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.response.OrderResponse
import com.ssafy.snuggle_final_app.data.service.OrderService
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class OrderViewModel(private val orderService: OrderService) : ViewModel() {

    private val _orderId = MutableLiveData<Int>()
    val orderId: LiveData<Int> get() = _orderId

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _orderDetail = MutableLiveData<OrderResponse>()
    val orderDetail: LiveData<OrderResponse> get() = _orderDetail

    suspend fun makeOrder(order: Order): Int {
        val response = orderService.makeOrder(order)
        if (response.isSuccessful) {
            return response.body() ?: throw IllegalStateException("서버에서 orderId가 반환되지 않았습니다.")
        } else {
            throw IllegalStateException("서버 응답 실패: ${response.code()}")
        }
    }

    fun getOrderDetail(orderId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val orderResponse = orderService.getOrderDetail(orderId)
                _orderDetail.value = orderResponse
            } catch (e: HttpException) {
                _error.value = "Error: ${e.message()}"
            } catch (e: IOException) {
                _error.value = "Network error. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Factory to create an instance of OrderViewModel
//    class Factory(private val orderService: OrderService) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return OrderViewModel(orderService) as T
//        }
//    }
}