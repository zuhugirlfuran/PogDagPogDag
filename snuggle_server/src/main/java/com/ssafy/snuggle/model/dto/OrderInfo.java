package com.ssafy.snuggle.model.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderInfo {
    private int orderId;         // 주문 ID (Primary Key)
    private String userId;          // 사용자 ID (Foreign Key)
    private double totalPrice;   // 총 가격
    private String name;         // 주문자 이름
    private String address;      // 배송 주소
    private String phone;        // 연락처
    private String userName;     // 사용자 이름 (추가 정보)
    private List<OrderDetail> details = new ArrayList<>();; // 주문 상세 목록

    // 기본 생성자
    public OrderInfo() {}

    // 매개변수 생성자
    public OrderInfo(int orderId, String userId, double totalPrice, String name, String address, String phone, String userName, List<OrderDetail> details) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.userName = userName;
        this.details = details;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

   

    public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	// toString() method for debugging
    @Override
    public String toString() {
        return "OrderInfo {" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", orderDetails=" + details +
                '}';
    }
}
