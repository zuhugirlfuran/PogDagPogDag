package com.ssafy.snuggle_final_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.snuggle_final_app.data.model.dto.Cart

class MainActivityViewModel : ViewModel() {
    //    private val _shoppingList = MutableLiveData<MutableList<Cart>>()
//    val shoppingList: LiveData<MutableList<Cart>> = _shoppingList
    private val _shoppingCart = MutableLiveData<List<Cart>>(emptyList())
    val shoppingCart: LiveData<List<Cart>> get() = _shoppingCart

    // Add to shopping list
//    fun addShoppingList(cartItem: Cart) {
//        val list = _shoppingCart.value ?: mutableListOf()
//        list.add(cartItem)
//        _shoppingCart.value = list
//    }

    fun addShoppingList(cart: Cart) {
        _shoppingCart.value = _shoppingCart.value?.plus(cart)
    }

    // Update shopping list
    fun updateShoppingList(updatedList: List<Cart>) {
        _shoppingCart.value = updatedList.toMutableList()
    }
}
