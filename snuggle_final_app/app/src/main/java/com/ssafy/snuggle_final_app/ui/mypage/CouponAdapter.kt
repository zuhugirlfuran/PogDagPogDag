package com.ssafy.snuggle_final_app.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.snuggle_final_app.data.model.dto.Coupon
import com.ssafy.snuggle_final_app.databinding.ItemCouponBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CouponAdapter(private val coupons: List<Coupon>) :
    RecyclerView.Adapter<CouponAdapter.CouponViewHolder>() {

    inner class CouponViewHolder(private val binding: ItemCouponBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coupon: Coupon) {
            binding.couponTvTitle.text = coupon.couponName

            // 날짜 포맷 변경
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
            try {
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
                    .parse(coupon.couponEnd.toString())
                val formattedDate = dateFormat.format(date!!)
                binding.couponTvDate.text = "$formattedDate 까지"
            } catch (e: Exception) {
                binding.couponTvDate.text = "날짜 형식 오류"
            }

            // 할인 정보 표시
            binding.couponTvDiscount.text =
                if (coupon.couponDiscount < 1) {
                    "${(coupon.couponDiscount * 100).toInt()}% 할인"
                } else {
                    "${coupon.couponDiscount.toInt()}원 할인"
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val binding = ItemCouponBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CouponViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.bind(coupons[position])
    }

    override fun getItemCount(): Int = coupons.size
}
