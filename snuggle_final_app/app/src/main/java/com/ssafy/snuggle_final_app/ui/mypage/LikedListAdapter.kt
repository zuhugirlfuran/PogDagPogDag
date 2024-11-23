package com.ssafy.snuggle_final_app.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Like

class LikedListAdapter(
    private val context: Context,
    private val dataList: List<Like>
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Like = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_like, parent, false)

        val img = view.findViewById<ImageView>(R.id.like_iv_img)


        val titleTextView = view.findViewById<TextView>(R.id.like_tv_title)
        val contentsTextView = view.findViewById<TextView>(R.id.like_tv_price)

//        titleTextView.text = "푸딩을 만들어보자"
//        contentsTextView.text = "영상 설명입니다~~"

        // 이미지 설정 (임시로 고정 이미지)
        img.setImageResource(R.drawable.item03)

        return view
    }


}
