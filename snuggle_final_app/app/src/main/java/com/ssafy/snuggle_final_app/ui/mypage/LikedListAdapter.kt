package com.ssafy.snuggle_final_app.ui.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma

class LikedListAdapter(
    private val context: Context,
    private val dataList: List<Product>,
    private val onClickItem: (Product) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Product = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_like, parent, false)

        val img = view.findViewById<ImageView>(R.id.like_iv_img)
        val titleTextView = view.findViewById<TextView>(R.id.like_tv_order_title)
        val priceTextView = view.findViewById<TextView>(R.id.like_tv_price)

        val product = getItem(position)

        // 데이터 설정
        titleTextView.text = product.productName
        priceTextView.text = makeComma(product.price)

        // Glide를 사용하여 이미지 로드
        Glide.with(context).load(product.image).into(img)

        // 클릭 이벤트 처리
        view.setOnClickListener {
            onClickItem(product) // 클릭 시 리스너 호출
        }

        return view
    }


}
