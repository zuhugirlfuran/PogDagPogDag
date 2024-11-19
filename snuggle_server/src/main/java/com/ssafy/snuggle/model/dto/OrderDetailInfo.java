package com.ssafy.snuggle.model.dto;

public class OrderDetailInfo {
	
    private int dId;             // 주문 상세 ID (Primary Key)
    private int orderId;         // 주문 ID (Foreign Key)
    private int userId;          // 사용자 ID (Foreign Key)
    private String userName;     // 사용자 이름 (추가 정보)
    private String orderTime;    // 주문 시간 (TIMESTAMP)
    private String completed;    // 완료 여부 ('Y' or 'N')
    private String productName;  // 상품 이름 (추가 정보)
    private int quantity;        // 주문 수량 (추가 정보)
    private double productPrice; // 상품 가격 (추가 정보)

    // 기본 생성자
    public OrderDetailInfo() {}

    // 매개변수 생성자
    public OrderDetailInfo(int dId, int orderId, int userId, String userName, String orderTime, String completed, String productName, int quantity, double productPrice) {
        this.dId = dId;
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.orderTime = orderTime;
        this.completed = completed;
        this.productName = productName;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    // Getter and Setter methods

    public int getDId() {
        return dId;
    }

    public void setDId(int dId) {
        this.dId = dId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailInfo {" +
                "dId=" + dId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", completed='" + completed + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                '}';
    }
}
