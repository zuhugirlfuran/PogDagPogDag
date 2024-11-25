package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Like;
import com.ssafy.snuggle.model.dto.Product;

public interface LikeService {

	/**
	 * Like을 등록한다. 사용자가 상품에서 좋아요 눌렀을 때
	 * 
	 * @param Like
	 */
	int addLike(Like favorite);

	/**
	 * 사용자가 좋아요 한 product 리스트 조회
	 * 
	 * @param userId
	 */
	List<Product> getLikeProductList(String userId);

	/**
	 * 유저와 상품의 좋아요 상태를 확인
	 * 
	 * @param userId
	 */
	boolean isLiked(String userId, int productId);
	

}
