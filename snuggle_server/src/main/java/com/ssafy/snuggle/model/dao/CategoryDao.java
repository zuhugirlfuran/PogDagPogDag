package com.ssafy.snuggle.model.dao;

import java.util.List;

import com.ssafy.snuggle.model.dto.Category;
import com.ssafy.snuggle.model.dto.Product;

public interface CategoryDao {

	/**
     * 카테고리 id에 따른 카테고리 조회
     * @param categoryId
     * @return
     */
	Category select(int cId);
	

	/**
     * 모든 카테고리 조회
     * @param
     * @return
     */
	List<Category> selectAll();

	/**
     * 카테고리 id에 따른 product 리스트 조회
     * @param categoryId
     * @return
     */
	List<Product> selectProductsByCategory(int cId);
	
	
}
