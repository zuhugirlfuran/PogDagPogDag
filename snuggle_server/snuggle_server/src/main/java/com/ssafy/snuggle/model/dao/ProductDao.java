package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Product;
import com.ssafy.snuggle.model.dto.ProductWithComment;

public interface ProductDao {

	/**
	 * 모든 상품정보를 조회한다.
	 * 
	 * @return
	 */
	List<Product> selectAll();

	/**
	 * ID에 해당하는 상품의 comment 갯수, 평점평균, 전체 판매량 정보를 함께 반환
	 * 
	 * @param productId
	 * @return
	 */
	ProductWithComment selectWithInfo(Integer productId);
	
	/**
	 * 베스트 TOP 5 의 상품을 불러온다.
	 * 
	 * @param 
	 * @return
	 */
	List<Product> selectBestProduct();
	
	/**
	 * 새로 생성된 상품 5개를 불러온다.
	 * 
	 * @param 
	 * @return
	 */
	List<Product> selectNewProduct();
	
	/**
	 * 가격 내림차순(낮은 가격순)으로 정렬
	 * 
	 * @param 
	 * @return
	 */
	List<Product> selectSortDesc();
	
	/**
	 * 가격 오름차순(높은 가격순)으로 정렬
	 * 
	 * @param 
	 * @return
	 */
	List<Product> selectSortAsc();
	
}
