package com.ssafy.snuggle.model.dto;

import java.sql.Timestamp;

public class Notification {

	private int nId;
	private String userId;
	private String title;
	private String content;
	private String channel;
	private Boolean isSubscribed;
	private Timestamp time;

	public Notification() {
	}

	
	public Notification(int nId, String userId, String title, String content, String channel, Boolean isSubscribed,
			Timestamp time) {
		super();
		this.nId = nId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.channel = channel;
		this.isSubscribed = isSubscribed;
		this.time = time;
	}


	public int getnId() {
		return nId;
	}

	public void setnId(int nId) {
		this.nId = nId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Boolean getIsSubscribed() {
		return isSubscribed;
	}

	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "Notification [nId=" + nId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", channel=" + channel + ", isSubscribed=" + isSubscribed + ", time=" + time + "]";
	}

	

}
