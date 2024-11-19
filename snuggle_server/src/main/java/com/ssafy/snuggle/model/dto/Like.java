package com.ssafy.snuggle.model.dto;

public class Like {
    private int likeId;         // 좋아요 ID (Primary Key)
    private int userId;         // 사용자 ID (Foreign Key)
    private int productId;      // 상품 ID (Foreign Key)
    private String likeTime;    // 좋아요 시간 (TIMESTAMP)

    // 기본 생성자
    public Like() {}

    // 매개변수 생성자
    public Like(int likeId, int userId, int productId, String likeTime) {
        this.likeId = likeId;
        this.userId = userId;
        this.productId = productId;
        this.likeTime = likeTime;
    }

    // Getter and Setter methods

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
    }

    @Override
    public String toString() {
        return "Like{" +
                "likeId=" + likeId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", likeTime='" + likeTime + '\'' +
                '}';
    }
}
