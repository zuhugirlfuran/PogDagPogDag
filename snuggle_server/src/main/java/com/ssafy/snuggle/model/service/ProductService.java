package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Product;
import com.ssafy.snuggle.model.dto.ProductWithComment;

public interface ProductService {

	/**
	 * 모든 상품 정보를 반환한다.
	 * 
	 * @return
	 */
	List<Product> getProductList();

	/**
	 * 상품의 정보, 판매량, 평점 정보를 함께 반환
	 * 
	 * @param productId
	 * @return
	 */
	ProductWithComment selectWithComment(int productId);

}
