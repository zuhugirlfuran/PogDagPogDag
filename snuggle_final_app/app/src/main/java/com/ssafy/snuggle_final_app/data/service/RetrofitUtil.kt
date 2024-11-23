package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.fcm.FirebaseTokenService

class RetrofitUtil {
    companion object {
        val commentService = ApplicationClass.retrofit.create(CommentService::class.java)
        val orderService = ApplicationClass.retrofit.create(OrderService::class.java)
        val likeService = ApplicationClass.retrofit.create(LikeService::class.java)
        val productService = ApplicationClass.retrofit.create(ProductService::class.java)
        val userService = ApplicationClass.retrofit.create(UserService::class.java)
        val categoryService = ApplicationClass.retrofit.create(CategoryService::class.java)

        val notificationService = ApplicationClass.retrofit.create(NotificationService::class.java)
        val fcmService = ApplicationClass.retrofit.create(FirebaseTokenService::class.java)
    }
}