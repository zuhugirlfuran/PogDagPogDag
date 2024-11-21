package com.ssafy.snuggle.model.dto;

public class Category {
	
	
	private int cId;
	private String categoryName;
	
	public Category() {}
	
	public Category(int cId, String categoryName) {
		super();
		this.cId = cId;
		this.categoryName = categoryName;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [cId=" + cId + ", categoryName=" + categoryName + "]";
	}
	
	
	
	

}
