package com.ssafy.snuggle.model.dto;

public class Comment {
    private int commentId;      // 댓글 ID (Primary Key)
    private int productId;      // 상품 ID (Foreign Key)
    private String userId;         // 사용자 ID (Foreign Key)
    private String comment;     // 댓글 내용

    // 기본 생성자
    public Comment() {}

	public Comment(int commentId, int productId, String userId, String comment) {
		super();
		this.commentId = commentId;
		this.productId = productId;
		this.userId = userId;
		this.comment = comment;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getProductId() {
		return productId;
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

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", productId=" + productId + ", userId=" + userId + ", comment="
				+ comment + "]";
	}

    // 매개변수 생성자
    
    
}
