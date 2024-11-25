package com.ssafy.snuggle.model.dto;

public class Comment {
    private int cId;      // 댓글 ID (Primary Key)
    private int productId;      // 상품 ID (Foreign Key)
    private String userId;         // 사용자 ID (Foreign Key)
    private String comment;     // 댓글 내용

    // 기본 생성자
    public Comment() {}

    // 매개변수 생성자
    public Comment(int cId, int productId, String userId, String comment) {
        this.cId = cId;
        this.productId = productId;
        this.userId = userId;
        this.comment = comment;
    }

    // Getter and Setter methods

    public int getProductId() {
        return productId;
    }

    public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "Comment{" +
                "cId=" + cId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
