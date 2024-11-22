package com.ssafy.snuggle.model.dto;

public class Address {

	private int addressId;
	private String userId;
	private String address;
	private String phone;

	public Address() {
	}

	public Address(int addressId, String userId, String address, String phone) {
		super();
		this.addressId = addressId;
		this.userId = userId;
		this.address = address;
		this.phone = phone;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", userId=" + userId + ", address=" + address + ", phone=" + phone
				+ "]";
	}

}
