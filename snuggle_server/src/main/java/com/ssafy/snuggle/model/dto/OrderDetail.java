package com.ssafy.snuggle.model.dto;

public class OrderDetail {
    private int dId;             // 주문 상세 ID (Primary Key)
    private int orderId;         // 주문 ID (Foreign Key)
    private int productId;       // 상품 ID
    private int quantity;
    private String orderTime;    // 주문 시간 (TIMESTAMP)
    private String completed;    // 완료 여부 ('Y' or 'N')

    // 기본 생성자
    public OrderDetail() {}

	public OrderDetail(int dId, int orderId, int productId, int quantity, String orderTime, String completed) {
		super();
		this.dId = dId;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.orderTime = orderTime;
		this.completed = completed;
	}

	public int getdId() {
		return dId;
	}

	public void setdId(int dId) {
		this.dId = dId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "OrderDetail [dId=" + dId + ", orderId=" + orderId + ", productId=" + productId + ", quantity="
				+ quantity + ", orderTime=" + orderTime + ", completed=" + completed + "]";
	}

    
}
