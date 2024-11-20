package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.snuggle.model.dao.LikeDao;
import com.ssafy.snuggle.model.dto.Like;
import com.ssafy.snuggle.model.dto.Product;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeDao lDao;

	@Override
	@Transactional
	public int addLike(Like like) {
		String userId = like.getUserId();
		int productId = like.getProductId();
		int result = 0;

		// userId와 productId로 중복 좋아요 체크
		boolean exists = lDao.isLike(userId, productId);

		if (exists) { // 이미 좋아요가 존재하면 삭제
			lDao.delete(userId, productId); // 좋아요 삭제
			lDao.decreaseLikeCount(productId); // 좋아요 수 감소
		} else { // 좋아요가 존재하지 않으면 추가
			result = lDao.insert(userId, productId); // 좋아요 추가
		}

		// 좋아요 추가 또는 삭제 후, 상품의 like_count를 갱신
//		if (result > 0 || exists) {
		System.out.println("갱신");
		lDao.updateLikeCount(productId); // 최신 like_count로 갱신
//		}

		return result;
	}

	@Override
	public List<Product> getLikeProductList(String userId) {

		return lDao.selectByUser(userId);
	}

}
