package com.ssafy.snuggle_final_app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.snuggle_final_app.data.model.dto.Cart

class MainActivityViewModel : ViewModel() {
    private val _shoppingList = MutableLiveData<MutableList<Cart>>()
    val shoppingList: LiveData<MutableList<Cart>> = _shoppingList

    // Add to shopping list
    fun addShoppingList(cartItem: Cart) {
        val list = _shoppingList.value ?: mutableListOf()
        list.add(cartItem)
        _shoppingList.value = list
    }

    // Update shopping list
    fun updateShoppingList(updatedList: List<Cart>) {
        _shoppingList.value = updatedList.toMutableList()
    }
}
