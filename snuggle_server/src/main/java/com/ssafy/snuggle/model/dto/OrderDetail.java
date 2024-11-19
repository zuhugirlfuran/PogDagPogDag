package com.ssafy.snuggle.model.dto;

public class OrderDetail {
    private int dId;             // 주문 상세 ID (Primary Key)
    private int orderId;         // 주문 ID (Foreign Key)
    private String userId;          // 사용자 ID (Foreign Key)
    private String orderTime;    // 주문 시간 (TIMESTAMP)
    private String completed;    // 완료 여부 ('Y' or 'N')

    // 기본 생성자
    public OrderDetail() {}

    // 매개변수 생성자
    public OrderDetail(int dId, int orderId, String userId, String orderTime, String completed) {
        this.dId = dId;
        this.orderId = orderId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.completed = completed;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return "OrderDetail {" +
                "dId=" + dId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", orderTime='" + orderTime + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}
