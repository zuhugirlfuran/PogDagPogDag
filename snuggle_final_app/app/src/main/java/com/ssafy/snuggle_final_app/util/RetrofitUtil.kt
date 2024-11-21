package com.ssafy.snuggle_final_app.util

import com.ssafy.snuggle_final_app.ApplicationClass
import com.ssafy.snuggle_final_app.service.ProductService
import com.ssafy.snuggle_final_app.service.UserService

class RetrofitUtil {
    companion object{
//        val commentService = ApplicationClass.retrofit.create(CommentService::class.java)
//        val orderService = ApplicationClass.retrofit.create(OrderService::class.java)
        val productService = ApplicationClass.retrofit.create(ProductService::class.java)
        val userService = ApplicationClass.retrofit.create(UserService::class.java)
    }
}