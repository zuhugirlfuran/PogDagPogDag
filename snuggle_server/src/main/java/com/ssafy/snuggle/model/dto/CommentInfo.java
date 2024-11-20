package com.ssafy.snuggle.model.dto;

// 댓글에 대한 기본 정보와 추가 정보를 효율적으로 관리
public class CommentInfo {

	private int commentId; // 댓글 ID (Primary Key)
	private int productId; // 상품 ID (Foreign Key)
	private String userId; // 사용자 ID (Foreign Key)
	private String comment; // 댓글 내용
	private String nickname; // 댓글 작성자의 이름

	// 기본 생성자
	public CommentInfo() {
	}

	// 매개변수 생성자 (모든 필드 초기화)
	public CommentInfo(int commentId, int productId, String userId, String comment, String nickname) {
		this.commentId = commentId;
		this.productId = productId;
		this.userId = userId;
		this.comment = comment;
		this.nickname = nickname;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "CommentWithInfo{" + "commentId=" + commentId + ", productId=" + productId + ", userId=" + userId
				+ ", comment='" + comment + '\'' + ", nickname='" + nickname + '\'' + '}';
	}
}
