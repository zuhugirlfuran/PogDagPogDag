package com.ssafy.snuggle.model.dto;

public class Product {
	
    private int productId;      // 상품 ID (Primary Key)
    private int cId;            // 카테고리 ID (Foreign Key)
    private String productName; // 상품 이름
    private int price;       // 상품 가격
    private String img;         // 상품 이미지
    private int likeCount;      // 좋아요 수

    // 기본 생성자
    public Product() {}

    // 매개변수 생성자
    public Product(int productId, int cId, String productName, int price, String img, int likeCount) {
        this.productId = productId;
        this.cId = cId;
        this.productName = productName;
        this.price = price;
        this.img = img;
        this.likeCount = likeCount;
    }

    // Getter and Setter methods

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCId() {
        return cId;
    }

    public void setCId(int cId) {
        this.cId = cId;
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

    // toString() method for debugging
    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", cId=" + cId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", likeCount=" + likeCount +
                '}';
    }
}
