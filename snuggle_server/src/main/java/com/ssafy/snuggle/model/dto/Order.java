package com.ssafy.snuggle.model.dto;

import java.util.List;

public class Order {

	private int orderId; // 주문 ID (Primary Key)
	private String userId; // 사용자 ID (Foreign Key)
	private int addressId;
	private double totalPrice; // 총 가격
	
	private List<OrderDetail> details;

	// 기본 생성자
	public Order() {
	}

	// 매개변수 생성자
	public Order(int orderId, String userId, double totalPrice) {
		this.orderId = orderId;
		this.userId = userId;
		this.totalPrice = totalPrice;
		
	}

	// Getter and Setter methods

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", userId=" + userId + ", addressId=" + addressId + ", totalPrice="
				+ totalPrice + "]";
	}


}