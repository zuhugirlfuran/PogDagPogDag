package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Coupon;

public interface CouponDao {

	/**
     * coupon 조회
     * 사용자 id와 사용여부가 N이면서
     * start 날짜에서 end 날짜 내의 쿠폰만
     * @param userId
     * @return
     */
	List<Coupon> select(String userId);
	
	
	/**
     * coupon 수정
     * 사용자가 가진 쿠폰의 사용여부 Y로 변경
     * @param Coupon
     * @return
     */
	int update(Coupon coupon);
	
	
}
