package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.CommentDao;
import com.ssafy.snuggle.model.dao.ProductDao;
import com.ssafy.snuggle.model.dao.StampDao;
import com.ssafy.snuggle.model.dto.CommentInfo;
import com.ssafy.snuggle.model.dto.Product;
import com.ssafy.snuggle.model.dto.ProductWithComment;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao pDao;

	@Autowired
	private CommentDao cDao;


	@Override
	public List<Product> getProductList() {
		return pDao.selectAll();
	}

	@Override
	public ProductWithComment selectWithComment(int productId) {

		// comment 정보
		List<CommentInfo> comments = cDao.selectByProduct(productId);
		ProductWithComment product = pDao.selectWithInfo(productId);

		// 총주문량, 평가횟수, 평균평점 계산
//		int commentCnt = comments.size(); // 총 평가횟수
//		int totalSells = sDao.selectTotalQuantityAll(); // 총 주문량

//		product.setCommentCount(commentCnt);
//		product.setTotalSells(totalSells);

		return product;
	}

	@Override
	public List<Product> getBestProduct() {

		return pDao.selectBestProduct();
	}

	@Override
	public List<Product> getNewProduct() {
		return pDao.selectNewProduct();
	}

	@Override
	public List<Product> getSortDesc() {

		return pDao.selectSortDesc();
	}

	@Override
	public List<Product> getSortAsc() {
		return pDao.selectSortAsc();
	}

}
