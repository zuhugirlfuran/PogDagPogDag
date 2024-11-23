package com.ssafy.snuggle.model.service;

import java.util.List;

import com.ssafy.snuggle.model.dto.Category;
import com.ssafy.snuggle.model.dto.Product;

public interface CategoryService {
	
	/**
     * 카테고리 이름을 조회한다.
     * @param cId
     */
	Category select(int cId);
	
	/**
     * 모든 카테고리 조회
     * @param
     */
	List<Category> selectAll();

	/**
     * 카테고리 id에 따른 상품 리스트들을 조회
     * @param cId
     */
	List<Product> selectProductsByCategory(int cId);

	
}
