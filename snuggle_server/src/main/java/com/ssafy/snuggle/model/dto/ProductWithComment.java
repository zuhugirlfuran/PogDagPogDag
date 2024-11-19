package com.ssafy.snuggle.model.dto;

import java.util.List;

public class ProductWithComment {
    private int productId;               // 상품 ID (Primary Key)
    private int cId;                     // 카테고리 ID
    private String productName;          // 상품 이름
    private double price;                // 상품 가격
    private String img;                  // 상품 이미지
    private int likeCount;               // 좋아요 수

    private List<CommentInfo> comments;  // 댓글 리스트

    // 기본 생성자
    public ProductWithComment() {}

    // Product 기반 생성자
    public ProductWithComment(Product product) {
        this.productId = product.getProductId();
        this.cId = product.getCId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.img = product.getImg();
        this.likeCount = product.getLikeCount();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public List<CommentInfo> getComments() {
        return comments;
    }

    public void setComments(List<CommentInfo> comments) {
        this.comments = comments;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "ProductWithComment{" +
                "productId=" + productId +
                ", cId=" + cId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", likeCount=" + likeCount +
                ", comments=" + comments +
                '}';
    }
}
