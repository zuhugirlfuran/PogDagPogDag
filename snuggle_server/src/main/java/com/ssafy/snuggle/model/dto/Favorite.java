package com.ssafy.snuggle.model.dto;

public class Favorite {
	 private int favoriteId;     // 즐겨찾기 ID (Primary Key)
	    private String userId;         // 사용자 ID (Foreign Key)
	    private String taggingId;      // 태깅 ID (Foreign Key)
	    private String isValid;     // 유효 여부 ('Y' or 'N')

	    // 기본 생성자
	    public Favorite() {}

	    // 매개변수 생성자
	    public Favorite(int favoriteId, String userId, String taggingId, String isValid) {
	        this.favoriteId = favoriteId;
	        this.userId = userId;
	        this.taggingId = taggingId;
	        this.isValid = isValid;
	    }

	    // Getter and Setter methods

	    public int getfavoriteId() {
	        return favoriteId;
	    }

	    public void setfavoriteId(int favoriteId) {
	        this.favoriteId = favoriteId;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public String getTaggingId() {
	        return taggingId;
	    }

	    public void setTaggingId(String taggingId) {
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
	                "favoriteId=" + favoriteId +
	                ", userId=" + userId +
	                ", taggingId=" + taggingId +
	                ", isValid='" + isValid + '\'' +
	                '}';
	    }
	}