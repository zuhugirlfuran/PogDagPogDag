package com.ssafy.snuggle.model.dto;

import java.sql.Timestamp;

public class Notification {

	private int n_id;
	private String user_id;
	private String title;
	private String content;
	private Timestamp time;

	public Notification() {
	}

	public Notification(int n_id, String user_id, String title, String content, Timestamp time) {
		super();
		this.n_id = n_id;
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.time = time;
	}

	public int getN_id() {
		return n_id;
	}

	public void setN_id(int n_id) {
		this.n_id = n_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Notification [n_id=" + n_id + ", user_id=" + user_id + ", title=" + title + ", content=" + content
				+ ", time=" + time + "]";
	}

}
