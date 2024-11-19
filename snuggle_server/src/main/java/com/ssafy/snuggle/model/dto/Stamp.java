package com.ssafy.snuggle.model.dto;

public class Stamp {
    private int sId;            // 스탬프 ID (Primary Key)
    private String userId;         // 사용자 ID (Foreign Key)
    private int orderId;        // 주문 ID (Foreign Key)
    private int quantity;       // 스탬프 개수

    // 기본 생성자
    public Stamp() {}

    // 매개변수 생성자
    public Stamp(int sId, String userId, int orderId, int quantity) {
        this.sId = sId;
        this.userId = userId;
        this.orderId = orderId;
        this.quantity = quantity;
    }
    
 // 매개변수 생성자
    public Stamp(String userId, int orderId, int quantity) {
        this.userId = userId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    // Getter and Setter methods

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "Stamp{" +
                "sId=" + sId +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                '}';
    }
}
