package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.CouponDao;
import com.ssafy.snuggle.model.dto.Coupon;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponDao cDao;
	
	@Override
	public List<Coupon> select(String userId) {
		
		return cDao.select(userId);
	}

	@Override
	public int update(Coupon coupon) {
		return cDao.update(coupon);
	}

}
