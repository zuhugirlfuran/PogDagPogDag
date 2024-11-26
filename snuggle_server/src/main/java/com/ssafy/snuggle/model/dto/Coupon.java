package com.ssafy.snuggle.model.dto;

import java.sql.Timestamp;
public class Coupon {

	private int couponId;
	private String userId;
	private String couponName;
	private Timestamp couponStart;
	private Timestamp couponEnd;
	private double couponDiscount;
	private boolean couponUse;
	
	public Coupon() {}

	
	public Coupon(int couponId, String userId, String couponName, Timestamp couponStart, Timestamp couponEnd,
			double couponDiscount, boolean couponUse) {
		super();
		this.couponId = couponId;
		this.userId = userId;
		this.couponName = couponName;
		this.couponStart = couponStart;
		this.couponEnd = couponEnd;
		this.couponDiscount = couponDiscount;
		this.couponUse = couponUse;
	}


	public int getCouponId() {
		return couponId;
	}


	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getCouponName() {
		return couponName;
	}


	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}


	public Timestamp getCouponStart() {
		return couponStart;
	}


	public void setCouponStart(Timestamp couponStart) {
		this.couponStart = couponStart;
	}


	public Timestamp getCouponEnd() {
		return couponEnd;
	}


	public void setCouponEnd(Timestamp couponEnd) {
		this.couponEnd = couponEnd;
	}


	public double getCouponDiscount() {
		return couponDiscount;
	}


	public void setCouponDiscount(double couponDiscount) {
		this.couponDiscount = couponDiscount;
	}


	public boolean isCouponUse() {
		return couponUse;
	}


	public void setCouponUse(boolean couponUse) {
		this.couponUse = couponUse;
	}


	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", userId=" + userId + ", couponName=" + couponName + ", couponStart="
				+ couponStart + ", couponEnd=" + couponEnd + ", couponDiscount=" + couponDiscount + ", couponUse="
				+ couponUse + "]";
	}
	
	
}
