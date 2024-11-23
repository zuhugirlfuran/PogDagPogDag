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

	/**
	 * 베스트 TOP 5 의 상품을 불러온다.
	 *
	 * @param
	 * @return
	 */
	List<Product> getBestProduct();

	/**
	 * 새로 생성된 상품 5개를 불러온다.
	 *
	 * @param
	 * @return
	 */
	List<Product> getNewProduct();

	/**
	 * 가격 내림차순(낮은 가격순)으로 정렬
	 *
	 * @param
	 * @return
	 */
	List<Product> getSortDesc();

	/**
	 * 가격 오름차순(높은 가격순)으로 정렬
	 *
	 * @param
	 * @return
	 */
	List<Product> getSortAsc();


}
