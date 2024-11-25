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
import com.ssafy.snuggle_final_app.data.model.dto.Tagging

class BookmarkAdapter(
    private val context: Context,
    private val dataList: MutableList<Tagging>
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Tagging = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_bookmark, parent, false)

        val img = view.findViewById<ImageView>(R.id.bookmark_iv_img)
        val titleTextView = view.findViewById<TextView>(R.id.bookmark_tv_title)
        val contentsTextView = view.findViewById<TextView>(R.id.bookmark_tv_contents)

        val taggingData = getItem(position)

        titleTextView.text = taggingData.videoTitle
        contentsTextView.text = taggingData.videoContent

        // 이미지 설정 (임시로 고정 이미지)
        Glide.with(context).load(taggingData.videoSrc).into(img)

        return view
    }

    fun updateData(newData: List<Tagging>) {
        dataList.clear()
        dataList.addAll(newData)

        notifyDataSetChanged()
    }


}
