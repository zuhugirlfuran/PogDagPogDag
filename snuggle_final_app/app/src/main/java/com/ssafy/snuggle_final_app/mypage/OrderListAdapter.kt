package com.ssafy.snuggle_final_app.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.dto.OrderDetail

class OrderListAdapter(
    private val context: Context,
    private val dataList: List<Pair<Order, List<OrderDetail>>>
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Pair<Order, List<OrderDetail>> = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_orderlist, parent, false)

        val img = view.findViewById<ImageView>(R.id.orderlist_iv_img)
        val titleTextView = view.findViewById<TextView>(R.id.orderlist_tv_order_title)
        val deliverTextView = view.findViewById<TextView>(R.id.orderlist_tv_delivery)
        val priceTextView = view.findViewById<TextView>(R.id.orderlist_tv_price)

        val (order, orderDetails) = getItem(position)

        // 주문 타이틀 설정
        val firstProductName = getFirstProductName(orderDetails)
        titleTextView.text = "$firstProductName 외 ${orderDetails.size - 1}건"

        // 배송 상태 설정
        val isCompleted = orderDetails.all { it.completed == "Y" }
        deliverTextView.text = if (isCompleted) "배송완료" else "배송중"

        // 총 가격 표시
        priceTextView.text = "${order.totalPrice.toInt()}원"

        // 이미지 설정 (임시로 고정 이미지)
        img.setImageResource(R.drawable.item03)

        return view
    }

    private fun getFirstProductName(orderDetails: List<OrderDetail>): String {
        val productMap = mapOf(
            1 to "푸딩 거북이",
            2 to "도토리를 줍자",
            3 to "도시락 세트"
        )
        val firstProductId = orderDetails.firstOrNull()?.productId
        return productMap[firstProductId] ?: "알 수 없음"
    }
}
