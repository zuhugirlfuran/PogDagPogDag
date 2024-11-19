package com.ssafy.snuggle.model.dto;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userId; // 사용자 이메일 ID (Primary Key)
	private String password; // 비밀번호
	private String nickname; // 닉네임
	private int age; // 나이
	private String gender; // 성별 ('Y' or 'N')
	private String path; // 경로 (선택)
	private String token; // 토큰
	private String img; // 프로필 이미지 (선택)
	private Integer stamps;
	private List<Stamp> stampList = new ArrayList<>();

	// 기본 생성자
	public User() {
	}

	// 매개변수 생성자
	public User(String userId, String password, String nickname, int age, String gender, String path,
			String token, String img, Integer stamps, List<Stamp> stampList) {
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.age = age;
		this.gender = gender;
		this.path = path;
		this.token = token;
		this.img = img;
		this.stamps = stamps;
		this.stampList = stampList;
	}

	// Getter and Setter methods

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getStamps() {
		return stamps;
	}

	public void setStamps(Integer stamps) {
		this.stamps = stamps;
	}

	public List<Stamp> getStampList() {
		return stampList;
	}

	public void setStampList(List<Stamp> stampList) {
		this.stampList = stampList;
	}

	@Override
	public String toString() {
		return "User {" + "userId=" + userId + ", password='" + password + '\''
				+ ", nickname='" + nickname + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", path='" + path
				+ '\'' + ", token='" + token + '\'' + ", img='" + img + '\'' + ", stamps=" + stamps + ", stampList="
				+ stampList + '}';
	}
}
