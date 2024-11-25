package com.ssafy.snuggle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.snuggle.model.dto.Coupon;
import com.ssafy.snuggle.model.service.CouponService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/snuggle/coupon")
@CrossOrigin("*")
public class CouponController {
	
	@Autowired
	private CouponService couponService;
	
	@GetMapping("/{userId}")
	@Operation(summary = "사용자가 가진 쿠폰 리스트를 조회한다.")
	public List<Coupon> getCouponByUserId(@PathVariable String userId) {
		
		return couponService.select(userId);
	}
	
	@PutMapping("")
	@Operation(summary = "사용자가 가진 쿠폰 정보를 수정한다. 성공 시, 쿠폰 사용여부를 Y로 변환하고 coupon id를 반환하고 실패 시, -1 반환한다.")
	public int updateCoupon(@RequestBody Coupon coupon) {
		int result = couponService.update(coupon);
		if (result > 0) {
			return coupon.getCouponId();
		}
		return -1;
	}
	
	
	
	

}
