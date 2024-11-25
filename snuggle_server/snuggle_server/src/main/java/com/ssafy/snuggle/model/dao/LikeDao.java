package com.ssafy.snuggle.model.dao;

import java.util.List;
import com.ssafy.snuggle.model.dto.Like;
import com.ssafy.snuggle.model.dto.Product;

public interface LikeDao {

	/**
	 * Like 테이블에 정보를 추가한다. 좋아요를 누르면 해당 userId와 productId를 받아 t_like에 추가한다. 그리고 해당
	 * 상품의 likeCount를 1 증가시킨다.
	 */
	int insert(String userId, int productId);

	/**
	 * userId와 productId로 중복 좋아요 체크 (이미 유효한 좋아요가 있는지 확인)
	 */
	int countLike(String userId, int productId);

	/**
	 * 좋아요 삭제 삭제 시 해당 상품의 likeCount를 1 감소시킨다.
	 */
	void delete(String userId, int productId);

	/**
	 * 사용자가 좋아요한 상품 목록을 조회
	 */
	List<Product> selectByUser(String userId);

	/**
	 * 상품의 likeCount를 1 증가시킨다.
	 */
	void increaseLikeCount(int productId);

	/**
	 * 상품의 likeCount를 1 감소시킨다.
	 */
	void decreaseLikeCount(int productId);

	/**
	 * 상품의 likeCount 갱신한다.
	 */
	void updateLikeCount(int productId);
	
	/**
	 * 좋아요 ID로 특정 좋아요 조회
	 */
	Like selectBylikeId(int likeId);
}
