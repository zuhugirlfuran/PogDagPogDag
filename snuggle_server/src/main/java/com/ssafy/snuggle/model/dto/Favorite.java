package com.ssafy.snuggle.model.dto;

public class Favorite {
	 private int bookmarkId;     // 즐겨찾기 ID (Primary Key)
	    private String userId;         // 사용자 ID (Foreign Key)
	    private int taggingId;      // 태깅 ID (Foreign Key)
	    private String isValid;     // 유효 여부 ('Y' or 'N')

	    // 기본 생성자
	    public Favorite() {}

	    // 매개변수 생성자
	    public Favorite(int bookmarkId, String userId, int taggingId, String isValid) {
	        this.bookmarkId = bookmarkId;
	        this.userId = userId;
	        this.taggingId = taggingId;
	        this.isValid = isValid;
	    }

	    // Getter and Setter methods

	    public int getBookmarkId() {
	        return bookmarkId;
	    }

	    public void setBookmarkId(int bookmarkId) {
	        this.bookmarkId = bookmarkId;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public int getTaggingId() {
	        return taggingId;
	    }

	    public void setTaggingId(int taggingId) {
	        this.taggingId = taggingId;
	    }

	    public String getIsValid() {
	        return isValid;
	    }

	    public void setIsValid(String isValid) {
	        this.isValid = isValid;
	    }

	    @Override
	    public String toString() {
	        return "Favorite{" +
	                "bookmarkId=" + bookmarkId +
	                ", userId=" + userId +
	                ", taggingId=" + taggingId +
	                ", isValid='" + isValid + '\'' +
	                '}';
	    }
	}