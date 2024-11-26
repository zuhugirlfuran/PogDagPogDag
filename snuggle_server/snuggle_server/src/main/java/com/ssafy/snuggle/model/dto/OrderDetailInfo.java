package com.ssafy.snuggle.model.dto;

public class OrderDetailInfo {

	private int dId; // 주문 상세 ID (Primary Key)
	private int orderId; // 주문 ID (Foreign Key)
	private int productId; // 상품 ID
	private int quantity;
	private String orderTime; // 주문 시간 (TIMESTAMP)
	private String completed; // 완료 여부 ('Y' or 'N')

	private String img; // 상품 이미지
	private int cId; // 카테고리 ID (Foreign Key)
	private int likeCount; // 좋아요 수
	private String productName; // 상품 이름
	private double productPrice; // 상품 가격

	// 기본 생성자
	public OrderDetailInfo() {
	}

	public OrderDetailInfo(int dId, int orderId, int productId, int quantity, String orderTime, String completed,
			String img, int cId, int likeCount, String productName, double productPrice) {
		super();
		this.dId = dId;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.orderTime = orderTime;
		this.completed = completed;
		this.img = img;
		this.cId = cId;
		this.likeCount = likeCount;
		this.productName = productName;
		this.productPrice = productPrice;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "OrderDetailInfo [dId=" + dId + ", orderId=" + orderId + ", productId=" + productId + ", quantity="
				+ quantity + ", orderTime=" + orderTime + ", completed=" + completed + ", img=" + img + ", cId=" + cId
				+ ", likeCount=" + likeCount + ", productName=" + productName + ", productPrice=" + productPrice + "]";
	}

}
