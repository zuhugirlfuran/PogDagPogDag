package com.ssafy.snuggle_final_app.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.service.OrderService
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil

class OrderViewModelFactory(private val orderService: OrderService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(orderService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
