package com.ssafy.snuggle.model.dto;

public class Product {

	private int productId; // 상품 ID (Primary Key)
	private int cId; // 카테고리 ID (Foreign Key)
	private String productName; // 상품 이름
	private int price; // 상품 가격
	private String content; // 상품 설명
	private String img; // 상품 이미지
	private int likeCount; // 좋아요 수

	// 기본 생성자
	public Product() {
	}

	// 매개변수 생성자
	public Product(int productId, int cId, String productName, int price, String content, String img, int likeCount) {
		this.productId = productId;
		this.cId = cId;
		this.productName = productName;
		this.price = price;
		this.content = content;
		this.img = img;
		this.likeCount = likeCount;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", cId=" + cId + ", productName=" + productName + ", price=" + price
				+ ", content=" + content + ", img=" + img + ", likeCount=" + likeCount + "]";
	}

}
