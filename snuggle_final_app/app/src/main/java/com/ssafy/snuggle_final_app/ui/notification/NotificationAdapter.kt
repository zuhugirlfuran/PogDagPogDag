
package com.ssafy.snuggle_final_app.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Notice

class NotificationAdapter(private val context: Context, private var dataList: List<Notice>) :
    BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_notification_notice, parent, false)

        val titleTextView = view.findViewById<TextView>(R.id.notification_tv_title)
        val subtitleTextView = view.findViewById<TextView>(R.id.notification_tv_contents)
        val img = view.findViewById<ImageView>(R.id.notification_item_ib)

        val item = dataList[position]
        titleTextView.text = item.title
        subtitleTextView.text = item.subTitle
        img.setImageResource(item.img)

        return view
    }

    // 데이터 갱신
    fun updateData(newData: List<Notice>) {
        dataList = newData
        notifyDataSetChanged()
    }
}
