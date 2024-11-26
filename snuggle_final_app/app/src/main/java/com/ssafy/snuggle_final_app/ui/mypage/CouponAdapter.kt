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
            binding.couponTvDate.text = "${coupon.couponEnd.substringBefore(" ")} 까지"

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
